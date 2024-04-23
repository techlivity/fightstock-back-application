package br.com.fight.stock.app.controller.carousel;

import br.com.fight.stock.app.domain.Carousel;
import br.com.fight.stock.app.domain.Image;
import br.com.fight.stock.app.exceptions.NotFoundCarouselException;
import br.com.fight.stock.app.repository.carousel.CarouselRepository;
import br.com.fight.stock.app.repository.image.ImageRepository;
import br.com.fight.stock.app.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static br.com.fight.stock.app.domain.Image.createImage;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    private final CarouselRepository carouselRepository;
    private final ImageRepository imageRepository;

    public CarouselController(CarouselRepository carouselRepository, ImageRepository imageRepository) {
        this.carouselRepository = carouselRepository;
        this.imageRepository = imageRepository;
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Cria um banner de carrosel", security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<DataResponse> createCarousel(@RequestParam(name = "file")
                                                       @Valid
                                                       @NotNull MultipartFile file, @RequestParam(name = "url") String url) throws IOException {
        //TODO: regra para verificar o formato da URL e logica para onde redirecionar.
        Image image = createImage(file);
        imageRepository.save(image);
        Carousel newCarousel = carouselRepository.save(new Carousel(image, url));
        return ResponseEntity.ok(new DataResponse(newCarousel));
    }

    @GetMapping
    @Operation(summary = "Recupera todos banner de carrosel")
    public ResponseEntity<DataResponse> getAllCarousel() {
        return ResponseEntity.ok(new DataResponse(carouselRepository.findAll()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(summary = "Remove um banner de carrosel", security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<DataResponse> deleteCarousel(@PathVariable(name = "id") Long id) {
        Optional<Carousel> carouselModel = carouselRepository.findById(id);
        if (carouselModel.isPresent()) {
            carouselRepository.deleteById(id);
            return ResponseEntity.ok(new DataResponse("Carrosel excluido com sucesso !"));
        } else {
            throw new NotFoundCarouselException(ApiUtils.formatMessage("erro ao excluir o id: %s não existe", id));
        }
    }

    record DataResponse(Object data) {
    }
}
