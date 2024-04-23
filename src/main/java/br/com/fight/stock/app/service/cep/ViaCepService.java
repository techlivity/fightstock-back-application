package br.com.fight.stock.app.service.cep;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCepService", url = "${api.cep.url}")
public interface ViaCepService {

    @GetMapping("{cep}/json/")
    String getCep(@PathVariable(name = "cep") String cep);
}
