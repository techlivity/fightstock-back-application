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
@Table(name = "tb_produtos_em_estoque")
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

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private ProductCategoryModel productCategoryModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getPromotion() {
        return promotion;
    }

    public void setPromotion(Boolean promotion) {
        this.promotion = promotion;
    }

    public ProductCategoryModel getProductCategoryModel() {
        return productCategoryModel;
    }

    public void setProductCategoryModel(ProductCategoryModel productCategoryModel) {
        this.productCategoryModel = productCategoryModel;
    }
}
