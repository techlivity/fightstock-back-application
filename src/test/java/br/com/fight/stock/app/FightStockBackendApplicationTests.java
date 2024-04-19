package br.com.fight.stock.app;

import br.com.fight.stock.app.configuration.TestDatabaseConfig;
import br.com.fight.stock.app.service.ViaCepService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestDatabaseConfig.class)
class FightStockBackendApplicationTests {

    @Test
    void contextLoads() {
        String s = "Mandioca";
        String b = "Aipim";
        assertNotEquals(s,b);
    }
}
