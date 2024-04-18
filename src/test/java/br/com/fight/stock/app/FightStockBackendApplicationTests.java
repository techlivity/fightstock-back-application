package br.com.fight.stock.app;

import br.com.fight.stock.app.configuration.TestDatabaseConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestDatabaseConfig.class)
class FightStockBackendApplicationTests {

    @Test
    void contextLoads() {
//		NavBarModel entity = new NavBarModel(1L, "xx", "xx");
//		NavBarModel entityFromDataBase = navBarRepository.save(entity);
//        assertNotNull(entityFromDataBase);
//		assertThat(entityFromDataBase).usingRecursiveComparison().isEqualTo(entity);
    }
}
