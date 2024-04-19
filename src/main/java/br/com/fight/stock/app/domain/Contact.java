package br.com.fight.stock.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_contato")
@JsonPropertyOrder(value = {"id","telefone_fixo","telefone_celular","telefone_ouvidoria","e-mail","endereco"})
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "telefone_fixo")
    @JsonProperty("telefone_fixo")
    @Pattern(regexp="\\+\\d{2}\\(\\d{2}\\)\\d{4}-\\d{4}", message = "invalid cell phone fixed must count the following format e.g. : +55(11)1234-5678")
    private String landLine;
    @Column(name = "telefone_celular")
    @JsonProperty("telefone_celular")
    @Pattern(regexp="\\+\\d{11,}", message = "invalid cell phone format must count the following format e.g. : +55055912345678")
    private String cellphone;
    @Column(name = "telefone_ouvidoria")
    @JsonProperty("telefone_ouvidoria")
    private  String ombudsman;
    @Column(name = "email")
    @JsonProperty("e-mail")
    @Email(message = "invalid e-mail format must count the following format e.g.: xxx@domain.com")
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonProperty("endereco")
    private Address address;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;
}
