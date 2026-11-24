package it.mauriziobotto.biblioteca.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.mauriziobotto.biblioteca.api.LettoreRequest;
import it.mauriziobotto.biblioteca.api.LibroRequest;
import it.mauriziobotto.biblioteca.api.PrestitoRequest;
import it.mauriziobotto.biblioteca.repository.LettoreRepository;
import it.mauriziobotto.biblioteca.repository.LibroRepository;
import it.mauriziobotto.biblioteca.repository.PrestitoRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrestitoServiceTest {

    @Autowired
    private LibroService libri;

    @Autowired
    private LettoreService lettori;

    @Autowired
    private PrestitoService prestiti;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LettoreRepository lettoreRepository;

    @Autowired
    private PrestitoRepository prestitoRepository;

    @AfterEach
    void pulisciDatabase() {
        prestitoRepository.deleteAll();
        libroRepository.deleteAll();
        lettoreRepository.deleteAll();
    }

    @Test
    void prestitoRendeLibroNonDisponibileFinoAllaRestituzione() {
        var libro = libri.crea(new LibroRequest("Il nome della rosa", "Umberto Eco"));
        var lettore = lettori.crea(new LettoreRequest("Ada Rossi"));

        var prestito = prestiti.crea(new PrestitoRequest(libro.id(), lettore.id()));

        assertThat(prestito.dataPrestito()).isEqualTo(LocalDate.now());
        assertThat(libri.elenco(true)).isEmpty();
        assertThatThrownBy(() -> prestiti.crea(new PrestitoRequest(libro.id(), lettore.id())))
                .isInstanceOf(PrestitoNonDisponibileException.class);

        var restituito = prestiti.registraRestituzione(prestito.id());

        assertThat(restituito.dataRestituzione()).isEqualTo(LocalDate.now());
        assertThat(libri.elenco(true)).hasSize(1);
        assertThat(prestiti.elenco(true)).isEmpty();
    }

    @Test
    void rifiutaPrestitoConRiferimentiInesistenti() {
        assertThatThrownBy(() -> prestiti.crea(new PrestitoRequest(99L, 99L)))
                .isInstanceOf(RisorsaNonTrovataException.class);
    }
}
