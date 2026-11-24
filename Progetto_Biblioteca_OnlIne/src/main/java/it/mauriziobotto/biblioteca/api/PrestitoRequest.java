package it.mauriziobotto.biblioteca.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PrestitoRequest(
        @NotNull @Positive Long libroId,
        @NotNull @Positive Long lettoreId) {
}
