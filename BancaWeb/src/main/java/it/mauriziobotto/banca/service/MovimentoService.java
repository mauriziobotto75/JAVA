package it.mauriziobotto.banca.service;

import it.mauriziobotto.banca.domain.Movimento;
import it.mauriziobotto.banca.domain.TipoMovimento;
import it.mauriziobotto.banca.repository.MovimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class MovimentoService {

    private final ContoService conti;
    private final MovimentoRepository movimenti;

    public MovimentoService(ContoService conti, MovimentoRepository movimenti) {
        this.conti = conti;
        this.movimenti = movimenti;
    }

    @Transactional
    public Movimento registra(String numeroConto, TipoMovimento tipo, BigDecimal importo,
                              String causale, LocalDate dataOperazione, LocalDate dataValuta) {
        if (tipo == null) {
            throw new RegolaApplicativaException("Tipo movimento non valido.");
        }
        if (importo == null || importo.signum() <= 0) {
            throw new RegolaApplicativaException("L'importo deve essere maggiore di zero.");
        }
        if (causale == null || causale.isBlank() || causale.length() > 40) {
            throw new RegolaApplicativaException("La causale è obbligatoria e non può superare 40 caratteri.");
        }
        if (dataOperazione == null || dataValuta == null) {
            throw new RegolaApplicativaException("Le date di operazione e valuta sono obbligatorie.");
        }

        var conto = conti.caricaPerAggiornamento(numeroConto);
        if (!conto.isOperativo()) {
            throw new RegolaApplicativaException("Il conto non è operativo.");
        }

        if (tipo.richiedeDisponibilita()) {
            var disponibile = conto.getSaldo().add(conto.getFido());
            if (importo.compareTo(disponibile) > 0) {
                throw new RegolaApplicativaException("Fondi insufficienti: saldo e fido non coprono l'importo.");
            }
        }

        var movimento = new Movimento(conto, dataOperazione, dataValuta, tipo, causale.trim(), importo);
        conto.applicaSaldo(tipo.variazione(importo));
        return movimenti.save(movimento);
    }
}
