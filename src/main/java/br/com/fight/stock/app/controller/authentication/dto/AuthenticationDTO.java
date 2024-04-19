package br.com.fight.stock.app.controller.authentication.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotNull(message = "email cannot be null") String email,
                                @NotNull(message = "password cannot be null") String password) {
}
