package br.com.fight.stock.app.controller.product;

import br.com.fight.stock.app.controller.product.dto.request.ProductRequest;
import br.com.fight.stock.app.controller.product.dto.response.ProductResponse;
import br.com.fight.stock.app.domain.Product;
import br.com.fight.stock.app.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Cria um produto", security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @PostMapping(value = "{productID}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Insere uma imagem em um produto", security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<String> insertImageOnProduct(@PathVariable(name = "productID") Long id,
                                                       @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.insertImage(id, file));
    }


    @GetMapping("/product")
    @Operation(summary = "Recupera um produto com uma especificação")
    public ResponseEntity<List<Product>> getProductsWithSpecification(@RequestParam(required = false) Boolean featured,
                                                                      @RequestParam(required = false) Boolean promotion,
                                                                      @RequestParam Boolean published) {
        return ResponseEntity.ok(productService.getProductWithSpecification(published, featured, promotion));
    }

    @GetMapping
    @Operation(summary = "Recupera um produto com uma data")
    public ResponseEntity<List<ProductResponse>> getProductWithTime(@Min(value = 1, message = "day must be greater than or equal to 1")
                                                                    @Max(value = 31, message = "day must be lower than or equal to 31")
                                                                    @RequestParam("day") int day,
                                                                    @Min(value = 1, message = "month must be greater than or equal to 1")
                                                                    @Max(value = 12, message = "month must be lower than or equal to 12")
                                                                    @RequestParam(value = "month", required = false) @DateTimeFormat(pattern = "MM") Integer month,
                                                                    @Min(value = 2023, message = "year must be greater than or equal to 2023")
                                                                    @Max(value = 2400, message = "year must be lower than or equal to 2400")
                                                                    @RequestParam(value = "year", required = false) @DateTimeFormat(pattern = "yyyy") Integer year) {
        return ResponseEntity.ok().body(productService.getProductWithDate(day, month, year));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Remove um produto", security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(productService.archiveProduct(id));
    }

    @PatchMapping("{productId}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Atualiza um produto", security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "productId") Long id,
                                                 @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok().body(productService.updateProduct(id, productRequest));
    }
}
