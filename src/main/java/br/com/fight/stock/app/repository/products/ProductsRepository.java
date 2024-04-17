package br.com.fight.stock.app.repository.products;

import br.com.fight.stock.app.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

        Optional<List<Product>> findByFeatured(Boolean featured);
        Optional<List<Product>> findByPromotion(Boolean promotion);
        Optional<List<Product>> findByPublished(Boolean published);
        Optional<List<Product>> findByFeaturedAndPublished(Boolean featured, Boolean published);
        Optional<List<Product>> findByPromotionAndPublished(Boolean promotion, Boolean published);
        Optional<List<Product>> findByFeaturedAndFiled(Boolean featured, Boolean filed);
        Optional<List<Product>> findByPromotionAndFiled(Boolean promotion, Boolean filed);
}
