package br.com.fight.stock.app.controller.categories;

import br.com.fight.stock.app.domain.CategoryModel;
import br.com.fight.stock.app.domain.ProductModel;
import br.com.fight.stock.app.exceptions.CategorieNotFoundException;
import br.com.fight.stock.app.exceptions.ProductNotFoundException;
import br.com.fight.stock.app.repository.categories.CategoriesRepository;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoriesController {


    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;

    public CategoriesController(CategoriesRepository categoriesRepository, ProductsRepository productsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoriesRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createCategorie(@RequestBody CategoryModel categoryModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriesRepository.save(categoryModel));
    }

    @PostMapping("/{name_category}/{id_product}")
    public ResponseEntity<?> insertProductsInCategories(@PathVariable(name = "name_category") String nameCategory, @PathVariable(name = "id_product") Long id) {
        //TODO melhorar tratamento usando ResponseStatusException com mensagem customizada e entidade de erro tratada
        CategoryModel categoryModel = categoriesRepository.findByName(nameCategory).orElseThrow(() -> new CategorieNotFoundException("Categories is not found!!!"));
        ProductModel product = productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product is not found!!!"));
        List<ProductModel> products = categoryModel.getProducts();
        products.add(product);
        categoriesRepository.save(categoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryModel);
    }

}
