package br.com.fight.stock.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@Import(TestDatabaseConfig.class)
class FightStockBackendApplicationTests {

    @Test
    void contextLoads() {
        //recebe da request
        FormularioProduto produto = new FormularioProduto();
        //salva no banco
        System.out.println(produto.formataMensagem());
        //devolve para a tela
        String[] split = produto.formataMensagem().split(";");
        for (String s : split) {
            System.out.println(s);
        }
    }
    static class FormularioProduto {
        String decription = "muito bom, atende a todos cenários, tambem pode ser usado na cozinha e afins";
        String dimensoes = "tem 2 metros de altura e 1 de largura";
        String caracteristicas = "torre quente que pode ser usado com forne e microndas";
        String peso = "70 kilos";
        String cor = "black piano";

        public String formataMensagem() {
            final String identificador = ";";
            return decription.concat(identificador)
                    .concat(dimensoes)
                    .concat(identificador)
                    .concat(caracteristicas)
                    .concat(identificador)
                    .concat(peso)
                    .concat(identificador)
                    .concat(cor)
                    .concat(identificador);
        }
    }
}
