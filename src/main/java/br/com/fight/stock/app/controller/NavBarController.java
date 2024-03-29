package br.com.fight.stock.app.controller;

import br.com.fight.stock.app.controller.response.DataResponse;
import br.com.fight.stock.app.domain.NavBarModel;
import br.com.fight.stock.app.repository.nav.NavBarRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("nav")
@Validated
public class NavBarController {

    private final NavBarRepository navBarRepository;

    public NavBarController(NavBarRepository navBarRepository) {
        this.navBarRepository = navBarRepository;
    }

    @PostMapping
    public ResponseEntity<NavBarModel> registerNewNavBar(@RequestBody @Valid @NotNull NavBarModel navBarModel) {
        //TODO: limite de nav bar.
        //TODO: associar categoria para criar uri de redirecionadores
        final NavBarModel newNav = navBarRepository.save(navBarModel);
        return ResponseEntity.ok(newNav);
    }

    @DeleteMapping("/{label}")
    public ResponseEntity<DataResponse> deleteNavBar(@PathVariable(name = "label") @NotNull String label) {
        //TODO: melhorar o código
        NavBarModel navBar = navBarRepository.findByLabel(label)
                .orElseThrow(() -> new RuntimeException("erro ao buscar nav através da label: " + label));
        navBarRepository.delete(navBar);
        return ResponseEntity.ok(new DataResponse("Item removido com sucesso !", null));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getNavBarList() {
        return ResponseEntity.ok(new DataResponse(navBarRepository.findAll(), null));
    }
}
