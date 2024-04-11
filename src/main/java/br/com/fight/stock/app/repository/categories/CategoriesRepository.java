package br.com.fight.stock.app.repository.categories;

import br.com.fight.stock.app.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
