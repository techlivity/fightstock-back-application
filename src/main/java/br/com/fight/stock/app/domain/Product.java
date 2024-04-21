package br.com.fight.stock.app.domain;

import br.com.fight.stock.app.controller.product.dto.request.ProductRequest;
import br.com.fight.stock.app.controller.product.dto.response.ProductResponse;
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
    private String name;
    @Column(name = "imagem")
    private Image image;
    @Column(name = "descricao")
    private String description;
    @Column(name = "em_destaque")
    private Boolean featured;
    @Column(name = "em_promocao")
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
                   String description,
                   Boolean featured,
                   Boolean promotion,
                   Boolean filed,
                   Boolean published) {
        this.name = name;
        this.description = description;
        this.featured = featured;
        this.promotion = promotion;
        this.filed = filed;
        this.published = published;
    }

    public static Product convertProductRequestToProduct(ProductRequest productRequest) {
        return new Product(
                productRequest.name(),
                productRequest.description(),
                productRequest.featured(),
                productRequest.promotion(), false, false);
    }

    public static Product convertProductRequestToProduct(ProductRequest productRequest, Product product) {
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPromotion(productRequest.promotion());
        product.setFeatured(productRequest.featured());
        return product;
    }

    public static ProductResponse convertProductToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getFiled(),
                product.getPublished(),
                convertInstantToLocalDateTime(product.getCreatedOn()),
                convertInstantToLocalDateTime(product.getLastUpdatedOn()),
                product.getName(),
                product.getImage(),
                product.getDescription(),
                product.getFeatured(),
                product.getPromotion());
    }
}
