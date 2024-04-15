package br.com.fight.stock.app.controller.categories;

import br.com.fight.stock.app.domain.Category;
import br.com.fight.stock.app.domain.Product;
import br.com.fight.stock.app.exceptions.CategorieNotFoundException;
import br.com.fight.stock.app.exceptions.NotFoundCategoryException;
import br.com.fight.stock.app.exceptions.ProductNotFoundException;
import br.com.fight.stock.app.repository.categories.CategoriesRepository;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("{nameCategory}")
    public ResponseEntity<?> getCategoryByName(@PathVariable(name = "nameCategory") String nameCategory) {
        return ResponseEntity.ok().body(categoriesRepository.findByName(nameCategory).orElseThrow(() -> new NotFoundCategoryException("Categoria não encontrada")));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER_ADMIN')")
    public ResponseEntity<?> createCategorie(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriesRepository.save(category));
    }

    @PostMapping("/{nameCategory}/{idProduct}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    public ResponseEntity<?> insertProductsInCategories(@PathVariable(name = "nameCategory") String nameCategory, @PathVariable(name = "idProduct") Long id) {
        Category category = categoriesRepository.findByName(nameCategory).orElseThrow(() -> new CategorieNotFoundException("Categories is not found!!!"));
        Product product = productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product is not found!!!"));
        List<Product> products = category.getProducts();
        products.add(product);
        categoriesRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

}
