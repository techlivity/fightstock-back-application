package br.com.fight.stock.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "endereco")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cep")
    private String cep;
    @Column(name = "estado")
    @JsonProperty("estado")
    private String state;
    @Column(name = "cidade")
    @JsonProperty("cidade")
    private String city;
    @Column(name = "logradouro")
    @JsonProperty("lograuro")
    private String patio;
    @Column(name = "numero")
    @JsonProperty("numero")
    private String number;
    @Column(name = "bairro")
    @JsonProperty("bairro")
    private String neighborhood;
    @Column(name = "complemento")
    @JsonProperty("complemento")
    private String complement;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;
}
