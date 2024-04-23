package br.com.fight.stock.app.controller.carousel;

import br.com.fight.stock.app.domain.Carousel;
import br.com.fight.stock.app.service.carousel.CarouselService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    private final CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Cria um banner de carrosel", security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<Carousel> createCarousel(@RequestParam(name = "file")
                                                   @Valid
                                                   @NotNull MultipartFile file, @RequestParam(name = "url") String url) throws IOException {
        return ResponseEntity.ok(carouselService.createCarousel(file, url));
    }

    @GetMapping
    @Operation(summary = "Recupera todos banner de carrosel")
    public ResponseEntity<List<Carousel>> getAllCarousel() {
        return ResponseEntity.ok(carouselService.getAllCarousel());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Remove um banner de carrosel", security = {@SecurityRequirement(name = "basicScheme")})
    public ResponseEntity<String> deleteCarousel(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(carouselService.deleteCarousel(id));
    }
}
