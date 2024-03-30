package br.com.fight.stock.app.controller;

import br.com.fight.stock.app.domain.CarouselModel;
import br.com.fight.stock.app.domain.NavBarModel;
import br.com.fight.stock.app.repository.carousel.CarouselRepository;
import br.com.fight.stock.app.repository.nav.NavBarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final NavBarRepository navBarRepository;
    private final CarouselRepository carouselRepository;

    public HomeController(NavBarRepository navBarRepository, CarouselRepository carouselRepository) {
        this.navBarRepository = navBarRepository;
        this.carouselRepository = carouselRepository;
    }

    @GetMapping
    public ResponseEntity<HomeRecord> getHome() {
        return ResponseEntity.ok(new HomeRecord(navBarRepository.findAll(), carouselRepository.findAll()));
    }

    //TODO: usar jackson propertie para mapear as entidades com snake case e no java camelCase
    record HomeRecord(List<NavBarModel> nav_bar, List<CarouselModel> carrousel){}
}
