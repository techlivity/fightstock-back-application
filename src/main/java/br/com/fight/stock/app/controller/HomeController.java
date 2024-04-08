package br.com.fight.stock.app.controller;

import br.com.fight.stock.app.domain.CarouselModel;
import br.com.fight.stock.app.domain.CategoryModel;
import br.com.fight.stock.app.domain.NavBarModel;
import br.com.fight.stock.app.domain.ProductModel;
import br.com.fight.stock.app.repository.categories.CategoriesRepository;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import br.com.fight.stock.app.repository.carousel.CarouselRepository;
import br.com.fight.stock.app.repository.nav.NavBarRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final NavBarRepository navBarRepository;
    private final CarouselRepository carouselRepository;
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;

    public HomeController(NavBarRepository navBarRepository, CarouselRepository carouselRepository, ProductsRepository productsRepository, CategoriesRepository categoriesRepository) {
        this.navBarRepository = navBarRepository;
        this.carouselRepository = carouselRepository;
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping
    public ResponseEntity<HomeRecord> getHome() {
        List<CategoryDTO> categoryDTOList = categoriesRepository.findAll()
                .stream()
                .map(this::convertCategoryModelToCategoryDTO)
                .toList();

        return ResponseEntity.ok(new HomeRecord(
                navBarRepository.findAll(),
                categoryDTOList,
                productsRepository.findAll(),
                carouselRepository.findAll()
        ));
    }

    private CategoryDTO convertCategoryModelToCategoryDTO(CategoryModel categoryModel) {
        return new CategoryDTO(categoryModel.getName(), categoryModel.getImageUrl(), categoryModel.getDescription());
    }

    //TODO: talvez pensar em uma entidade melhor com uma query caso existam mais de duas paginas de dados com limite de 10 produtos por exemplo.
    record CategoryDTO(String name, String imageUrl, String description) {
    }

    record HomeRecord(@JsonProperty("nav_bar") List<NavBarModel> navBar,
                      @JsonProperty("categorias") List<CategoryDTO> categoryModelList,
                      @JsonProperty("produtos") List<ProductModel> productModelList,
                      @JsonProperty("carrossel") List<CarouselModel> carousel) {
    }
}