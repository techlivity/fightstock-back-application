package br.com.fight.stock.app.controller.navbar;

import br.com.fight.stock.app.controller.navbar.response.DataResponse;
import br.com.fight.stock.app.domain.NavBarModel;
import br.com.fight.stock.app.exceptions.LabelNotFoundException;
import br.com.fight.stock.app.repository.nav.NavBarRepository;
import br.com.fight.stock.app.utils.ApiUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<DataResponse> deleteNavBar(@PathVariable(name = "label") String label) {
        Optional<NavBarModel> navBar = navBarRepository.findByLabel(label);
        if (navBar.isPresent()) {
            navBarRepository.delete(navBar.get());
            return ResponseEntity.ok(new DataResponse("Item removido com sucesso !"));
        } else {
            throw new LabelNotFoundException(ApiUtils.formatMessage("Erro ao excluir a label: %s, não existe", label));
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse> getNavBarList() {
        return ResponseEntity.ok(new DataResponse(navBarRepository.findAll()));
    }
}
