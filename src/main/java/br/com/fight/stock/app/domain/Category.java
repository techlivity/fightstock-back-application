package br.com.fight.stock.app.domain;

import br.com.fight.stock.app.controller.categories.dto.request.CategoriesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "categoria")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("imagem")
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;
    @JsonProperty("descrição")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="produto_id")
    @JsonProperty("produtos")
    private List<Product> products;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;

    public static Category convertCategoriesRequestToCategory(CategoriesRequest categoriesRequest, Category category) {
        category.setName(categoriesRequest.name());
        category.setDescription(categoriesRequest.description());
        return category;
    }
}
