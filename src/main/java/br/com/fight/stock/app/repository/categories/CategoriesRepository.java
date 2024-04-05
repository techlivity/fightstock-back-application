package br.com.fight.stock.app.repository.categories;

import br.com.fight.stock.app.domain.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoryModel, Long> {

    Optional<CategoryModel> findByName(String name);

}
