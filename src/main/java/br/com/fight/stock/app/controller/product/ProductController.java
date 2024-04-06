package br.com.fight.stock.app.controller.product;

import br.com.fight.stock.app.domain.ProductModel;
import br.com.fight.stock.app.exceptions.ProductNotFoundException;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductsRepository repository;

    public ProductController(ProductsRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductModel product) {
        ProductModel newProduct = repository.save(product);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/product")
    public ResponseEntity<?> getProductsWithQuery(@RequestParam(required = false) Boolean featured,
                                         @RequestParam(required = false) Boolean promotion) {
        if (featured != null && featured) {
            List<ProductModel> featuredProducts = repository.findByFeatured(true)
                    .orElseThrow(() -> new ProductNotFoundException("No featured products found"));
            return ResponseEntity.ok().body(featuredProducts);
        } else if (promotion != null && promotion) {
            List<ProductModel> promotionProducts = repository.findByPromotion(true)
                    .orElseThrow(() -> new ProductNotFoundException("No products on promotion found"));
            return ResponseEntity.ok().body(promotionProducts);
        } else {
            throw new IllegalArgumentException("You must specify either 'featured=true' or 'promotion=true' to get products.");
        }
    }
}
