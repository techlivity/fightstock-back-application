package br.com.fight.stock.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_categoria")
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome_categoria")
    @JsonProperty("nome")
    private String name;
    @Column(name = "image_categoria")
    @JsonProperty("image_url")
    private String imageUrl;
    @Column(name = "descricao_categoria")
    @JsonProperty("descrição")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="produto_id")
    @JsonProperty("produtos")
    private List<ProductModel> products;
}
