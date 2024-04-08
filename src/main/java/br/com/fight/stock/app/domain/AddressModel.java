package br.com.fight.stock.app.domain;

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
@Table(name = "tb_endereco")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cep")
    private String cep;
    @Column(name = "estado")
    private String state;
    @Column(name = "cidade")
    private String city;
    @Column(name = "logradouro")
    private String patio;
    @Column(name = "numero")
    private String number;
    @Column(name = "bairro")
    private String neighborhood;
    @Column(name = "complemento")
    private String complement;
}
