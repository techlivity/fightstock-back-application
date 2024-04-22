package br.com.fight.stock.app.service.product.core;

import br.com.fight.stock.app.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private ProductSpecification() {
    }

    public static Specification<Product> published(Boolean published) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("published"), published);
    }

    public static Specification<Product> featured(Boolean featured) {
        if (featured != null && featured) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("featured"));
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("featured"));
        }
    }

    public static Specification<Product> promotion(Boolean promotion) {
        if (promotion != null && promotion) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("promotion"));
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("promotion"));
        }
    }
}
