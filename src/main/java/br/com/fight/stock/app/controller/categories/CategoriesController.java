package br.com.fight.stock.app.controller.categories;

import br.com.fight.stock.app.controller.categories.dto.request.CategoriesRequest;
import br.com.fight.stock.app.domain.Category;
import br.com.fight.stock.app.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("categories")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Recupera todas as categorias cadastradas")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("{nameCategory}")
    @Operation(summary = "Recupera uma categoria com o nome")
    public ResponseEntity<?> getCategoryByName(@PathVariable(name = "nameCategory") String nameCategory) {
        return ResponseEntity.ok().body(categoryService.getCategoryByName(nameCategory));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Cria uma categoria", security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
    }

    @PostMapping("/{nameCategory}/{idProduct}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Insere um produto em uma categoria", security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<?> insertProductsInCategories(@PathVariable(name = "nameCategory") String nameCategory,
                                                        @PathVariable(name = "idProduct") Long idProduct) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.insertProductInCategory(nameCategory, idProduct));
    }

    @PostMapping(value = "{nameCategory}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(
            summary = "Insere uma image em uma categoria",
            security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<?> insertImageOnCategory(@RequestPart(name = "file") MultipartFile file,
                                                   @PathVariable(name = "nameCategory") String nameCategory) throws IOException {
        return ResponseEntity.ok(categoryService.insertImage(file, nameCategory));
    }

    @PatchMapping("{nameCategory}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Atualiza uma categoria", security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<Category> updateCategories(@PathVariable(name = "nameCategory") String nameCategory, @RequestBody CategoriesRequest categoriesRequest) {
        return ResponseEntity.status(HttpStatus.valueOf(201)).body(categoryService.updateCategory(categoriesRequest, nameCategory));
    }

    @DeleteMapping("{idCategory}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Exclui uma categoria", security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<?> deleteCategories(@PathVariable(name = "idCategory") Long idCategory) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(categoryService.deleteCategory(idCategory));
    }
}
