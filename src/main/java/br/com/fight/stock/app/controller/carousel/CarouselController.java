package br.com.fight.stock.app.controller.carousel;

import br.com.fight.stock.app.domain.Carousel;
import br.com.fight.stock.app.exceptions.NotFoundCarouselException;
import br.com.fight.stock.app.repository.carousel.CarouselRepository;
import br.com.fight.stock.app.utils.ApiUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    private final CarouselRepository carouselRepository;

    public CarouselController(CarouselRepository carouselRepository) {
        this.carouselRepository = carouselRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER_ADMIN')")
    public ResponseEntity<DataResponse> createCarousel(@RequestBody
                                                       @Valid
                                                       @NotNull Carousel carousel) {
        //TODO: regra para verificar o formato da URL.
        //TODO: regra para converção de imagem em base64 OU usar multi part na requisição
        Carousel newCarousel = carouselRepository.save(carousel);
        return ResponseEntity.ok(new DataResponse(newCarousel));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getAllCarousel() {
        return ResponseEntity.ok(new DataResponse(carouselRepository.findAll()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER_ADMIN')")
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
