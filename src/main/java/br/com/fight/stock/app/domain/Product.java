package br.com.fight.stock.app.domain;

import br.com.fight.stock.app.controller.product.dto.request.ProductRequest;
import br.com.fight.stock.app.controller.product.dto.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

import static br.com.fight.stock.app.utils.ApiUtils.convertInstantToLocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_produtos")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    @JsonProperty("nome")
    private String name;
    @Column(name = "image")
    @JsonProperty("image_url")
    private String imageUrl;
    @Column(name = "descricao")
    @JsonProperty("descrição")
    private String description;
    @Column(name = "em_destaque")
    @JsonProperty("em_destaque")
    private Boolean featured;
    @Column(name = "em_promocao")
    @JsonProperty("em_promoção")
    private Boolean promotion;
    @Column(name = "arquivado")
    private Boolean filed;
    @Column(name = "publicado")
    private Boolean published;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;

    public Product(String name,
                   String imageUrl,
                   String description,
                   Boolean featured,
                   Boolean promotion,
                   Boolean filed,
                   Boolean published) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.featured = featured;
        this.promotion = promotion;
        this.filed = filed;
        this.published = published;
    }

    public static Product convertProductRequestToProduct(ProductRequest productRequest) {
        return new Product(productRequest.name(),
                productRequest.imageURL(),
                productRequest.description(),
                productRequest.featured(),
                productRequest.promotion(), false, false);
    }

    public static ProductResponse convertProductToProductResponse(Product product) {
        return new ProductResponse(product.getFiled(),
                product.getPublished(),
                convertInstantToLocalDateTime(product.getCreatedOn()),
                convertInstantToLocalDateTime(product.getLastUpdatedOn()),
                product.getName(),
                product.getImageUrl(),
                product.getDescription(),
                product.getFeatured(),
                product.getPromotion());
    }
}
