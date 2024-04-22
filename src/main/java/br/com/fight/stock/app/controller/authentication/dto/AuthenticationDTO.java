package br.com.fight.stock.app.controller.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotNull(message = "email cannot be null")
                                @JsonProperty("e-mail") String email,
                                @NotNull(message = "password cannot be null")
                                @JsonProperty("senha") String password) {
}
