package br.com.fight.stock.app.controller.product;

import br.com.fight.stock.app.controller.product.dto.request.ProductRequest;
import br.com.fight.stock.app.controller.product.dto.request.TimeRequest;
import br.com.fight.stock.app.controller.product.dto.response.ProductResponse;
import br.com.fight.stock.app.domain.Product;
import br.com.fight.stock.app.exceptions.ProductNotFoundException;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import br.com.fight.stock.app.utils.ApiUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductsRepository repository;

    public ProductController(ProductsRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('USER_ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = Product.convertProductRequestToProduct(productRequest);
        Product newProduct = repository.save(product);
        ProductResponse productResponse = Product.convertProductToProductResponse(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProductsPublishedWithQuery(@RequestParam(required = false) Boolean featured,
                                                                       @RequestParam(required = false) Boolean promotion,
                                                                       @RequestParam Boolean published) {
        if (featured != null && featured) {
            List<Product> featuredProducts = repository.findByFeaturedAndPublished(true, published)
                    .orElseThrow(() -> new ProductNotFoundException("No featured products found"));
            return ResponseEntity.ok().body(featuredProducts);
        } else if (promotion != null && promotion) {
            List<Product> promotionProducts = repository.findByPromotionAndPublished(true, published)
                    .orElseThrow(() -> new ProductNotFoundException("No products on promotion found"));
            return ResponseEntity.ok().body(promotionProducts);
        } else {
            return ResponseEntity.ok().body(repository.findByPublished(published)
                    .orElseThrow(() -> new ProductNotFoundException("No products found !")));
        }
    }

    @GetMapping
    public ResponseEntity<?> getProductWithTime(@Min(value = 1, message = "day must be greater than or equal to 1")
                                                @Max(value = 31, message = "day must be lower than or equal to 31")
                                                @RequestParam("day") int day,
                                                @Min(value = 1, message = "month must be greater than or equal to 1")
                                                @Max(value = 12, message = "month must be lower than or equal to 12")
                                                @RequestParam(value = "month", required = false) @DateTimeFormat(pattern = "MM") Integer month,
                                                @Min(value = 2023, message = "year must be greater than or equal to 2023")
                                                @Max(value = 2400, message = "year must be lower than or equal to 2400")
                                                @RequestParam(value = "year", required = false) @DateTimeFormat(pattern = "yyyy") Integer year) {
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        List<Product> byCreationDate = repository.findByCreationDate(day, month, year);
        List<ProductResponse> productRequests = byCreationDate.stream()
                .map(Product::convertProductToProductResponse)
                .toList();
        return ResponseEntity.ok().body(productRequests);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().body("Product deleted successfully !");
    }

    @PatchMapping("{productId}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Transactional
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "productId") Long id,
                                                 @RequestBody ProductRequest productRequest) {
        Product product = repository.findById(id).map(product1 ->
                        repository.save(Product.convertProductRequestToProduct(productRequest, product1)))
                .orElseThrow(() -> new ProductNotFoundException("Product not found ! "));
        return ResponseEntity.ok().body(product);
    }
}
