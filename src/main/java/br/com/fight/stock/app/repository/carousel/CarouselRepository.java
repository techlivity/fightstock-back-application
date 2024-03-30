package br.com.fight.stock.app.repository.carousel;

import br.com.fight.stock.app.domain.CarouselModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends JpaRepository<CarouselModel, Long> {
}
