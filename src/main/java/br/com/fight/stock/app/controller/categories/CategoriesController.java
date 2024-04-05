package br.com.fight.stock.app.controller.categories;

import br.com.fight.stock.app.domain.CategoryModel;
import br.com.fight.stock.app.domain.ProductModel;
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
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoriesRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> postCategories(@RequestBody CategoryModel categoryModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriesRepository.save(categoryModel));
    }

    @PostMapping("/{name_category}")
    public ResponseEntity<?> insertCategoriesProducts(@RequestBody ProductModel productModel, @PathVariable(name = "name_category") String nameCategory){
        CategoryModel categoryModel = categoriesRepository.findByName(nameCategory).orElseThrow(() -> new RuntimeException("404 Categories is not found!!!"));
        productsRepository.save(productModel);
        List<ProductModel> products = categoryModel.getProducts();
        products.add(productModel);
        categoriesRepository.save(categoryModel);
        return  ResponseEntity.status(HttpStatus.CREATED).body(categoryModel);
    }

}
