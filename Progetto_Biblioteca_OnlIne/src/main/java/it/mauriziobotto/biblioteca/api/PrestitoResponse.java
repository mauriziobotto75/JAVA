package it.mauriziobotto.biblioteca.api;

import java.time.LocalDate;

public record PrestitoResponse(
        Long id,
        Long libroId,
        String titoloLibro,
        Long lettoreId,
        String nominativoLettore,
        LocalDate dataPrestito,
        LocalDate dataRestituzione) {
}
