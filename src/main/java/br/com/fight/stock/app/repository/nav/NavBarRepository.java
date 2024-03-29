package br.com.fight.stock.app.repository.nav;

import br.com.fight.stock.app.domain.NavBarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NavBarRepository extends JpaRepository<NavBarModel, Long> {
    Optional<NavBarModel> findByLabel(String label);
}
