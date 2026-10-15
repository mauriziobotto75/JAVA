package it.mauriziobotto.banca.service;

import it.mauriziobotto.banca.domain.Conto;
import it.mauriziobotto.banca.domain.Movimento;
import it.mauriziobotto.banca.domain.TipoMovimento;
import it.mauriziobotto.banca.repository.ContoRepository;
import it.mauriziobotto.banca.repository.MovimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final ContoRepository conti;
    private final MovimentoRepository movimenti;

    public ReportService(ContoRepository conti, MovimentoRepository movimenti) {
        this.conti = conti;
        this.movimenti = movimenti;
    }

    @Transactional(readOnly = true)
    public List<Conto> contiConSaldoNegativo() {
        return conti.findWithSaldoNegativo(BigDecimal.ZERO);
    }

    @Transactional(readOnly = true)
    public List<Movimento> perTipo(TipoMovimento tipo) {
        return movimenti.findByTipo(tipo);
    }

    @Transactional(readOnly = true)
    public List<Movimento> traDate(LocalDate dal, LocalDate al) {
        verificaIntervallo(dal, al);
        return movimenti.findBetweenDates(dal, al);
    }

    @Transactional(readOnly = true)
    public List<Movimento> perConto(String numero) {
        return movimenti.findByConto(numero);
    }

    @Transactional(readOnly = true)
    public List<Movimento> perContoEIntervallo(String numero, LocalDate dal, LocalDate al) {
        verificaIntervallo(dal, al);
        return movimenti.findByContoAndDateRange(numero, dal, al);
    }

    private void verificaIntervallo(LocalDate dal, LocalDate al) {
        if (dal == null || al == null || dal.isAfter(al)) {
            throw new RegolaApplicativaException("L'intervallo date non è valido.");
        }
    }
}
