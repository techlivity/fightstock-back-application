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
@Table(name = "tb_produtos")
public class ProductModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    @Column(name = "image")
    private String imageUrl;
    @Column(name = "descricao")
    private String description;
    @Column(name = "em_destaque")
    private Boolean featured;
    @Column(name = "em_promocao")
    private Boolean promotion;
}
