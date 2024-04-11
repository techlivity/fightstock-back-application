package br.com.fight.stock.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String landLine;
    @Column(name = "telefone_celular")
    @JsonProperty("telefone_celular")
    private String cellphone;
    @Column(name = "telefone_ouvidoria")
    @JsonProperty("telefone_ouvidoria")
    private  String ombudsman;
    @Column(name = "email")
    @JsonProperty("e-mail")
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonProperty("endereco")
    private Address address;
}
