package it.mauriziobotto.banca.service;

import it.mauriziobotto.banca.domain.Conto;
import it.mauriziobotto.banca.repository.ClienteRepository;
import it.mauriziobotto.banca.repository.ContoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ContoService {

    private final ContoRepository conti;
    private final ClienteRepository clienti;

    public ContoService(ContoRepository conti, ClienteRepository clienti) {
        this.conti = conti;
        this.clienti = clienti;
    }

    @Transactional(readOnly = true)
    public List<Conto> elenco() {
        return conti.findAllWithCliente();
    }

    @Transactional(readOnly = true)
    public Conto trova(String numero) {
        return conti.findWithClienteByNumero(numero)
                .orElseThrow(() -> new RegolaApplicativaException("Conto inesistente."));
    }

    @Transactional
    public Conto apri(String numero, String codiceCliente, String abi, String cab,
                      String filiale, LocalDate dataApertura, BigDecimal fido) {
        var numeroNormalizzato = numero.trim();
        if (conti.existsById(numeroNormalizzato)) {
            throw new RegolaApplicativaException("Esiste già un conto con questo numero.");
        }
        var cliente = clienti.findById(codiceCliente.trim())
                .orElseThrow(() -> new RegolaApplicativaException("Il cliente selezionato non esiste."));
        return conti.save(new Conto(
                numeroNormalizzato, cliente, normalizza(abi), normalizza(cab), normalizza(filiale),
                dataApertura, fido));
    }

    @Transactional
    public void aggiornaFido(String numero, BigDecimal fido) {
        var conto = caricaPerAggiornamento(numero);
        conto.aggiornaFido(fido);
    }

    @Transactional
    public void chiudi(String numero) {
        var conto = caricaPerAggiornamento(numero);
        conto.chiudi();
    }

    @Transactional
    public Conto caricaPerAggiornamento(String numero) {
        return conti.findWithClienteByNumeroForUpdate(numero)
                .orElseThrow(() -> new RegolaApplicativaException("Conto inesistente."));
    }

    private String normalizza(String valore) {
        return valore == null ? null : valore.trim();
    }
}
