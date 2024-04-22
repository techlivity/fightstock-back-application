package br.com.fight.stock.app.controller.authentication.dto.request;

import br.com.fight.stock.app.domain.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

    @Email(message = "invalid e-mail must count the following format e.g. : blablabla@domain")
    @JsonProperty("e-mail")
    private String email;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("senha")
    private String password;
    @JsonProperty("cargo")
    private List<Role> roles;
}
