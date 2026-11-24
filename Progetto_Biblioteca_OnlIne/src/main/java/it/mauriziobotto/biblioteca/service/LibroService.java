package it.mauriziobotto.biblioteca.service;

import it.mauriziobotto.biblioteca.api.LibroRequest;
import it.mauriziobotto.biblioteca.api.LibroResponse;
import it.mauriziobotto.biblioteca.domain.Libro;
import it.mauriziobotto.biblioteca.repository.LibroRepository;
import it.mauriziobotto.biblioteca.repository.PrestitoRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    private final LibroRepository libri;
    private final PrestitoRepository prestiti;

    public LibroService(LibroRepository libri, PrestitoRepository prestiti) {
        this.libri = libri;
        this.prestiti = prestiti;
    }

    @Transactional(readOnly = true)
    public List<LibroResponse> elenco(boolean soloDisponibili) {
        Set<Long> nonDisponibili = prestiti.findAllByDataRestituzioneIsNullOrderByDataPrestitoDescIdDesc()
                .stream()
                .map(prestito -> prestito.getLibro().getId())
                .collect(Collectors.toSet());
        return libri.findAll(org.springframework.data.domain.Sort.by("id")).stream()
                .filter(libro -> !soloDisponibili || !nonDisponibili.contains(libro.getId()))
                .map(libro -> toResponse(libro, !nonDisponibili.contains(libro.getId())))
                .toList();
    }

    @Transactional
    public LibroResponse crea(LibroRequest richiesta) {
        Libro libro = libri.save(new Libro(richiesta.titolo(), richiesta.autore()));
        return toResponse(libro, true);
    }

    private static LibroResponse toResponse(Libro libro, boolean disponibile) {
        return new LibroResponse(libro.getId(), libro.getTitolo(), libro.getAutore(), disponibile);
    }
}
