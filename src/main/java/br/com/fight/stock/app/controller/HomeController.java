package br.com.fight.stock.app.controller;

import br.com.fight.stock.app.domain.CarouselModel;
import br.com.fight.stock.app.domain.NavBarModel;
import br.com.fight.stock.app.domain.ProductModel;
import br.com.fight.stock.app.repository.products.ProductsRepository;
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
    private final ProductsRepository productsRepository;

    public HomeController(NavBarRepository navBarRepository, CarouselRepository carouselRepository, ProductsRepository productsRepository) {
        this.navBarRepository = navBarRepository;
        this.carouselRepository = carouselRepository;
        this.productsRepository = productsRepository;
    }

    @GetMapping
    public ResponseEntity<HomeRecord> getHome() {
        return ResponseEntity.ok(new HomeRecord(
                navBarRepository.findAll(),
                carouselRepository.findAll(),
                productsRepository.findAll()));
    }

    //TODO: usar jackson propertie para mapear as entidades com snake case e no java camelCase
    //TODO: talvez pensar em uma entidade melhor com uma query caso existam mais de duas paginas de dados com limite de 10 produtos por exemplo.
    record HomeRecord(List<NavBarModel> nav_bar, List<CarouselModel> carrousel, List<ProductModel> productModelList) {
    }
}
