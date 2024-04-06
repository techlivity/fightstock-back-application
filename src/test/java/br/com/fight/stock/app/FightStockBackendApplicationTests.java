package br.com.fight.stock.app;

import br.com.fight.stock.app.domain.NavBarModel;
import br.com.fight.stock.app.repository.nav.NavBarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FightStockBackendApplicationTests {

    @Autowired
    NavBarRepository navBarRepository;

    @Test
    void contextLoads() {
		NavBarModel entity = new NavBarModel(1L, "xx", "xx");
		NavBarModel entityFromDataBase = navBarRepository.save(entity);
        assertNotNull(entityFromDataBase);
		assertThat(entityFromDataBase).usingRecursiveComparison().isEqualTo(entity);
    }
}
