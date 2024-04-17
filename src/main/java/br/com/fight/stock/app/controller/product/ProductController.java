package br.com.fight.stock.app.controller.product;

import br.com.fight.stock.app.controller.product.dto.request.ProductRequest;
import br.com.fight.stock.app.controller.product.dto.response.ProductResponse;
import br.com.fight.stock.app.domain.Product;
import br.com.fight.stock.app.exceptions.ProductNotFoundException;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER_ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = Product.convertProductRequestToProduct(productRequest);
        Product newProduct = repository.save(product);
        ProductResponse productResponse = Product.convertProductToProductResponse(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProductsPublishedWithQuery(@RequestParam(required = false) Boolean featured,
                                                              @RequestParam(required = false) Boolean promotion, @RequestParam Boolean published) {
        if (featured != null && featured) {
            List<Product> featuredProducts = repository.findByFeaturedAndPublished(true, published)
                    .orElseThrow(() -> new ProductNotFoundException("No featured products found"));
            return ResponseEntity.ok().body(featuredProducts);
        } else if (promotion != null && promotion) {
            List<Product> promotionProducts = repository.findByPromotionAndPublished(true, published)
                    .orElseThrow(() -> new ProductNotFoundException("No products on promotion found"));
            return ResponseEntity.ok().body(promotionProducts);
        } else {
            throw new IllegalArgumentException("You must specify either 'featured=true' or 'promotion=true' to get products.");
        }
    }
}
