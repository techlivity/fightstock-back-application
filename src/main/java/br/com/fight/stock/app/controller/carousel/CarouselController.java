package br.com.fight.stock.app.controller.carousel;

import br.com.fight.stock.app.controller.navbar.response.DataResponse;
import br.com.fight.stock.app.domain.CarouselModel;
import br.com.fight.stock.app.exceptions.NotFoundCarouselException;
import br.com.fight.stock.app.repository.carousel.CarouselRepository;
import br.com.fight.stock.app.utils.ApiUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DataResponse> createCarousel(@RequestBody
                                                       @Valid
                                                       @NotNull CarouselModel carouselModel) {
        //TODO: regra para verificar o formato da URL.
        //TODO: regra para converção de imagem em base64 OU usar multi part na requisição
        CarouselModel newCarousel = carouselRepository.save(carouselModel);
        return ResponseEntity.ok(new DataResponse(newCarousel));
    }

    @GetMapping
    public ResponseEntity<DataResponse> getAllCarousel() {
        return ResponseEntity.ok(new DataResponse(carouselRepository.findAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteCarousel(@PathVariable(name = "id") Long id) {
        Optional<CarouselModel> carouselModel = carouselRepository.findById(id);
        if (carouselModel.isPresent()) {
            carouselRepository.deleteById(id);
            return ResponseEntity.ok(new DataResponse("Carrosel excluido com sucesso !"));
        } else {
            throw new NotFoundCarouselException(ApiUtils.formatMessage("erro ao excluir o id: %s não existe", id));
        }
    }
}
