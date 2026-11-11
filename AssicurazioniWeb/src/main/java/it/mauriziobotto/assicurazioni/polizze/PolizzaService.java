package it.mauriziobotto.assicurazioni.polizze;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PolizzaService {

    private final PolizzaRepository repository;

    public PolizzaService(PolizzaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Polizza> cerca(String query, String stato) {
        if (query != null && !query.isBlank()) {
            String termine = query.strip();
            if (stato != null && !stato.isBlank()) {
                return repository
                        .findAllByStatoPagamentoAndCodicePolizzaContainingIgnoreCaseOrStatoPagamentoAndNomeClienteContainingIgnoreCaseOrderByScadenzaAscCodicePolizzaAsc(
                                stato, termine, stato, termine);
            }
            return repository.findAllByCodicePolizzaContainingIgnoreCaseOrNomeClienteContainingIgnoreCaseOrderByScadenzaAscCodicePolizzaAsc(
                    termine, termine);
        }
        if (stato != null && !stato.isBlank()) {
            return repository.findAllByStatoPagamentoOrderByScadenzaAscCodicePolizzaAsc(stato);
        }
        return repository.findAllByOrderByScadenzaAscCodicePolizzaAsc();
    }

    @Transactional(readOnly = true)
    public Polizza trova(String codice) {
        return repository.findById(normalizeCode(codice))
                .orElseThrow(() -> new PolizzaNonTrovataException(codice));
    }

    public Polizza crea(Polizza polizza) {
        polizza.setCodicePolizza(normalizeCode(polizza.getCodicePolizza()));
        if (repository.existsById(polizza.getCodicePolizza())) {
            throw new CodicePolizzaDuplicatoException(polizza.getCodicePolizza());
        }
        polizza.setStatoPagamento(Polizza.NON_PAGATA);
        polizza.setDataPagamento(null);
        return repository.save(polizza);
    }

    public Polizza aggiorna(String codice, Polizza dati) {
        Polizza esistente = trova(codice);
        esistente.setNomeCliente(dati.getNomeCliente());
        esistente.setIndirizzo(dati.getIndirizzo());
        esistente.setImporto(dati.getImporto());
        esistente.setScadenza(dati.getScadenza());
        esistente.setSiglaAuto(dati.getSiglaAuto());
        esistente.setNumeroCavalli(dati.getNumeroCavalli());
        return repository.save(esistente);
    }

    public Polizza registraPagamento(String codice, LocalDate dataPagamento) {
        Polizza polizza = trova(codice);
        polizza.registraPagamento(dataPagamento);
        return repository.save(polizza);
    }

    public void annulla(String codice) {
        repository.delete(trova(codice));
    }

    private static String normalizeCode(String codice) {
        if (codice == null || codice.isBlank()) {
            throw new IllegalArgumentException("Il codice polizza è obbligatorio.");
        }
        return codice.strip();
    }
}
