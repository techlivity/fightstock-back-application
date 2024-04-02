package br.com.fight.stock.app.controller.product;

import br.com.fight.stock.app.domain.ProductModel;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductsRepository repository;

    public ProductController(ProductsRepository repository) {
        this.repository = repository;
    }

    //TODO: criar método para associar categoria a produto sem categoria por ex:
    //ProductCategoryModel category = categoryRepository.findById(categoryId)
    //.orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + categoryId));
    //product.setProductCategoryModel(category);
    //productRepository.save(product);
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductModel product) {
        ProductModel newProduct = repository.save(product);
        return ResponseEntity.ok(newProduct);
    }
    @GetMapping("/{featured}")
    public ResponseEntity<?> getProductOnFeatured(@PathVariable(name = "featured") Boolean featured) {
        Optional<List<ProductModel>> featured1 = repository.findByFeatured(featured);
        if (featured1.isPresent())
            return ResponseEntity.ok(featured1.get());

        return ResponseEntity.ok(new ArrayList<>());
    }
}
