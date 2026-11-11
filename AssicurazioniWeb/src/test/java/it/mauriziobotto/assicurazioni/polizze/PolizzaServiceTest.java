package it.mauriziobotto.assicurazioni.polizze;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PolizzaServiceTest {

    @Autowired
    private PolizzaService service;

    @Autowired
    private PolizzaRepository repository;

    @AfterEach
    void pulisciDatabase() {
        repository.deleteAll();
    }

    @Test
    void creaPolizzaNonPagataERegistraPagamento() {
        Polizza polizza = new Polizza("POL0000001");
        polizza.setNomeCliente("Ada Rossi");
        polizza.setImporto(new BigDecimal("125.50"));
        polizza.setScadenza(LocalDate.of(2027, 12, 31));

        service.crea(polizza);
        Polizza salvata = service.trova("POL0000001");
        assertThat(salvata.getStatoPagamento()).isEqualTo(Polizza.NON_PAGATA);
        assertThat(salvata.getDataPagamento()).isNull();

        service.registraPagamento("POL0000001", LocalDate.of(2026, 11, 11));
        Polizza pagata = service.trova("POL0000001");
        assertThat(pagata.getStatoPagamento()).isEqualTo(Polizza.PAGATA);
        assertThat(pagata.getDataPagamento()).isEqualTo(LocalDate.of(2026, 11, 11));
    }

    @Test
    void annullamentoRimuoveLaPolizzaDallArchivio() {
        Polizza polizza = new Polizza("POL0000002");
        service.crea(polizza);

        service.annulla("POL0000002");

        assertThat(repository.existsById("POL0000002")).isFalse();
    }

    @Test
    void rifiutaUnCodicePolizzaGiaPresente() {
        service.crea(new Polizza("POL0000003"));

        assertThatThrownBy(() -> service.crea(new Polizza("POL0000003")))
                .isInstanceOf(CodicePolizzaDuplicatoException.class);
    }
}
