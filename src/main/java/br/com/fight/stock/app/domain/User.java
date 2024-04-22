package br.com.fight.stock.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "invalid e-mail must count the following format e.g. : blablabla@domain")
    @JsonProperty("e-mail")
    private String email;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("senha")
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("imagem")
    private Image image;
    @CreationTimestamp
    @JsonProperty("criado_em")
    private Instant createdOn;
    @UpdateTimestamp
    @JsonProperty("atualizado_em")
    private Instant lastUpdatedOn;
    @ManyToMany
    @JsonProperty("cargo")
    private List<Role> roles;
}