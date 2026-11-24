package it.mauriziobotto.biblioteca.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LettoreRequest(@NotBlank @Size(max = 120) String nominativo) {
}
