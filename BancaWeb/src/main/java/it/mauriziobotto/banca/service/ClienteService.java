package it.mauriziobotto.banca.service;

import it.mauriziobotto.banca.domain.Cliente;
import it.mauriziobotto.banca.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienti;

    public ClienteService(ClienteRepository clienti) {
        this.clienti = clienti;
    }

    @Transactional(readOnly = true)
    public List<Cliente> elenco() {
        return clienti.findAll(org.springframework.data.domain.Sort.by("codice"));
    }

    @Transactional(readOnly = true)
    public Cliente trova(String codice) {
        return clienti.findById(codice)
                .orElseThrow(() -> new RegolaApplicativaException("Cliente inesistente."));
    }

    @Transactional
    public Cliente salva(String codice, String nome, String indirizzo) {
        var codiceNormalizzato = codice.trim();
        var cliente = clienti.findById(codiceNormalizzato)
                .orElseGet(() -> new Cliente(codiceNormalizzato, nome.trim(), indirizzo.trim()));
        cliente.setNome(nome.trim());
        cliente.setIndirizzo(indirizzo.trim());
        return clienti.save(cliente);
    }
}
