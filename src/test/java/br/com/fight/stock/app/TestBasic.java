package br.com.fight.stock.app;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestBasic {

    @Test
    public void shoudTest_Simple() {
        String numeroTelefone = "+55055984245522";

        // Expressão regular para o padrão +55055984245522
        String regex = "\\+\\d{11,}";

        // Compilando a expressão regular
        Pattern pattern = Pattern.compile(regex);

        // Criando um Matcher para comparar o número de telefone com o padrão
        Matcher matcher = pattern.matcher(numeroTelefone);

        // Verificando se o número de telefone corresponde ao padrão
        if (matcher.matches()) {
            System.out.println("Número de telefone válido!");
        } else {
            System.out.println("Número de telefone inválido!");
        }
    }
}
