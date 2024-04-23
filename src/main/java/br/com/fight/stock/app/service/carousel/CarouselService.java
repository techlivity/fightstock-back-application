package br.com.fight.stock.app.service.carousel;

import br.com.fight.stock.app.domain.Carousel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarouselService {

    Carousel createCarousel(MultipartFile file, String url) throws IOException;
    List<Carousel> getAllCarousel();
    String deleteCarousel(Long id);
}
