package it.mauriziobotto.biblioteca.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LibroRequest(
        @NotBlank @Size(max = 200) String titolo,
        @NotBlank @Size(max = 120) String autore) {
}
