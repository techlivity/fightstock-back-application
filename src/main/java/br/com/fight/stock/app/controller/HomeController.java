package br.com.fight.stock.app.controller;

import br.com.fight.stock.app.domain.CarouselModel;
import br.com.fight.stock.app.domain.CategoryModel;
import br.com.fight.stock.app.domain.ContactModel;
import br.com.fight.stock.app.domain.ProductModel;
import br.com.fight.stock.app.repository.carousel.CarouselRepository;
import br.com.fight.stock.app.repository.categories.CategoriesRepository;
import br.com.fight.stock.app.repository.contact.ContactRepository;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CarouselRepository carouselRepository;
    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ContactRepository contactRepository;

    public HomeController(CarouselRepository carouselRepository, ProductsRepository productsRepository, CategoriesRepository categoriesRepository, ContactRepository contactRepository) {;
        this.carouselRepository = carouselRepository;
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.contactRepository = contactRepository;
    }

    @GetMapping
    public ResponseEntity<HomeRecord> getHome() {
        List<CategoryDTO> categoryDTOList = categoriesRepository.findAll()
                .stream()
                .map(this::convertCategoryModelToCategoryDTO)
                .toList();

        return ResponseEntity.ok(new HomeRecord(
                categoryDTOList,
                productsRepository.findAll(),
                carouselRepository.findAll(),
                contactRepository.findAll()
        ));
    }

    private CategoryDTO convertCategoryModelToCategoryDTO(CategoryModel categoryModel) {
        return new CategoryDTO(categoryModel.getName(), categoryModel.getImageUrl(), categoryModel.getDescription());
    }

    //TODO: talvez pensar em uma entidade melhor com uma query caso existam mais de duas paginas de dados com limite de 10 produtos por exemplo.
    record CategoryDTO(String name, String imageUrl, String description) {
    }

    record HomeRecord(@JsonProperty("categorias") List<CategoryDTO> categoryModelList,
                      @JsonProperty("produtos") List<ProductModel> productModelList,
                      @JsonProperty("carrossel") List<CarouselModel> carousel,
                      @JsonProperty("contato")List<ContactModel> contactModels) {
    }
}