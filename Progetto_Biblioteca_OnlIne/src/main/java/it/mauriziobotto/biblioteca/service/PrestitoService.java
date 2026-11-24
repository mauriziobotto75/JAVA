package it.mauriziobotto.biblioteca.service;

import it.mauriziobotto.biblioteca.api.PrestitoRequest;
import it.mauriziobotto.biblioteca.api.PrestitoResponse;
import it.mauriziobotto.biblioteca.domain.Lettore;
import it.mauriziobotto.biblioteca.domain.Libro;
import it.mauriziobotto.biblioteca.domain.Prestito;
import it.mauriziobotto.biblioteca.repository.LettoreRepository;
import it.mauriziobotto.biblioteca.repository.LibroRepository;
import it.mauriziobotto.biblioteca.repository.PrestitoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestitoService {

    private final LibroRepository libri;
    private final LettoreRepository lettori;
    private final PrestitoRepository prestiti;

    public PrestitoService(
            LibroRepository libri, LettoreRepository lettori, PrestitoRepository prestiti) {
        this.libri = libri;
        this.lettori = lettori;
        this.prestiti = prestiti;
    }

    @Transactional(readOnly = true)
    public List<PrestitoResponse> elenco(boolean soloAttivi) {
        List<Prestito> trovati = soloAttivi
                ? prestiti.findAllByDataRestituzioneIsNullOrderByDataPrestitoDescIdDesc()
                : prestiti.findAllByOrderByDataPrestitoDescIdDesc();
        return trovati.stream().map(PrestitoService::toResponse).toList();
    }

    @Transactional
    public PrestitoResponse crea(PrestitoRequest richiesta) {
        Libro libro = libri.findByIdForUpdate(richiesta.libroId())
                .orElseThrow(() -> new RisorsaNonTrovataException("Libro", richiesta.libroId()));
        Lettore lettore = lettori.findById(richiesta.lettoreId())
                .orElseThrow(() -> new RisorsaNonTrovataException("Lettore", richiesta.lettoreId()));
        if (prestiti.existsByLibroIdAndDataRestituzioneIsNull(libro.getId())) {
            throw new PrestitoNonDisponibileException("Il libro ha già un prestito attivo.");
        }
        Prestito prestito = prestiti.save(new Prestito(libro, lettore, LocalDate.now()));
        return toResponse(prestito);
    }

    @Transactional
    public PrestitoResponse registraRestituzione(Long id) {
        Prestito prestito = prestiti.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Prestito", id));
        if (!prestito.isAttivo()) {
            throw new PrestitoNonDisponibileException("Il prestito risulta già restituito.");
        }
        prestito.registraRestituzione(LocalDate.now());
        return toResponse(prestiti.save(prestito));
    }

    private static PrestitoResponse toResponse(Prestito prestito) {
        return new PrestitoResponse(
                prestito.getId(),
                prestito.getLibro().getId(),
                prestito.getLibro().getTitolo(),
                prestito.getLettore().getId(),
                prestito.getLettore().getNominativo(),
                prestito.getDataPrestito(),
                prestito.getDataRestituzione());
    }
}
