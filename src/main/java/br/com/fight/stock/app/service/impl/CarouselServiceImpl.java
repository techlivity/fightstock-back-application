package br.com.fight.stock.app.service.impl;

import br.com.fight.stock.app.domain.Carousel;
import br.com.fight.stock.app.domain.Image;
import br.com.fight.stock.app.exceptions.NotFoundCarouselException;
import br.com.fight.stock.app.repository.carousel.CarouselRepository;
import br.com.fight.stock.app.repository.image.ImageRepository;
import br.com.fight.stock.app.service.carousel.CarouselService;
import br.com.fight.stock.app.utils.ApiUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static br.com.fight.stock.app.domain.Image.createImage;

@Service
public class CarouselServiceImpl implements CarouselService {

    private final CarouselRepository carouselRepository;
    private final ImageRepository imageRepository;

    public CarouselServiceImpl(CarouselRepository carouselRepository, ImageRepository imageRepository) {
        this.carouselRepository = carouselRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Carousel createCarousel(MultipartFile file, String url) throws IOException {
        //TODO: regra para verificar o formato da URL e logica para onde redirecionar.
        Image image = createImage(file);
        imageRepository.save(image);
        return carouselRepository.save(new Carousel(image, url));
    }

    @Override
    public List<Carousel> getAllCarousel() {
        return carouselRepository.findAll();
    }

    @Override
    public String deleteCarousel(Long id) {
        Optional<Carousel> carouselModel = carouselRepository.findById(id);
        if (carouselModel.isPresent()) {
            carouselRepository.deleteById(id);
            return "Carrosel excluido com sucesso !";
        }else {
            throw new NotFoundCarouselException(ApiUtils.formatMessage("erro ao excluir o id: %s não existe", id));
        }
    }
}
