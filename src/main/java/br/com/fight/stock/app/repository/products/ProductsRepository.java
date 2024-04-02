package br.com.fight.stock.app.repository.products;

import br.com.fight.stock.app.domain.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<ProductModel, Long> {

    Optional<List<ProductModel>> findByFeatured(Boolean featured);
}
