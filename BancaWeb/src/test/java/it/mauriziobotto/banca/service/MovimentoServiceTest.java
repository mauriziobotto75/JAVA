package it.mauriziobotto.banca.service;

import it.mauriziobotto.banca.domain.Cliente;
import it.mauriziobotto.banca.domain.Conto;
import it.mauriziobotto.banca.domain.TipoMovimento;
import it.mauriziobotto.banca.repository.ClienteRepository;
import it.mauriziobotto.banca.repository.ContoRepository;
import it.mauriziobotto.banca.repository.MovimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class MovimentoServiceTest {

    @Autowired
    private MovimentoService servizio;

    @Autowired
    private ClienteRepository clienti;

    @Autowired
    private ContoRepository conti;

    @Autowired
    private MovimentoRepository movimenti;

    @BeforeEach
    void preparaConto() {
        movimenti.deleteAll();
        conti.deleteAll();
        clienti.deleteAll();
        var cliente = clienti.save(new Cliente("CLI001", "Mario Rossi", "Via Roma 1"));
        conti.save(new Conto("CONTO0000001", cliente, "01234", "05678", "0001",
                LocalDate.of(2025, 1, 1), new BigDecimal("100.00")));
    }

    @Test
    void registraMovimentoEaggiornaIlSaldoNellaStessaOperazione() {
        servizio.registra("CONTO0000001", TipoMovimento.VE, new BigDecimal("150.00"),
                "Versamento", LocalDate.now(), LocalDate.now());
        servizio.registra("CONTO0000001", TipoMovimento.PA, new BigDecimal("200.00"),
                "Pagamento", LocalDate.now(), LocalDate.now());

        assertThat(conti.findById("CONTO0000001").orElseThrow().getSaldo())
                .isEqualByComparingTo("-50.00");
        assertThat(movimenti.count()).isEqualTo(2);
    }

    @Test
    void rifiutaAddebitoOltreSaldoPiuFidoSenzaScrivereMovimento() {
        assertThatThrownBy(() -> servizio.registra(
                "CONTO0000001", TipoMovimento.PR, new BigDecimal("100.01"),
                "Prelievo", LocalDate.now(), LocalDate.now()))
                .isInstanceOf(RegolaApplicativaException.class)
                .hasMessageContaining("Fondi insufficienti");

        assertThat(conti.findById("CONTO0000001").orElseThrow().getSaldo())
                .isEqualByComparingTo("0.00");
        assertThat(movimenti.count()).isZero();
    }

    @Test
    void rifiutaMovimentiSuContoChiuso() {
        var conto = conti.findById("CONTO0000001").orElseThrow();
        conto.chiudi();
        conti.save(conto);

        assertThatThrownBy(() -> servizio.registra(
                "CONTO0000001", TipoMovimento.VE, new BigDecimal("10.00"),
                "Versamento", LocalDate.now(), LocalDate.now()))
                .isInstanceOf(RegolaApplicativaException.class)
                .hasMessageContaining("non è operativo");

        assertThat(movimenti.count()).isZero();
    }
}
