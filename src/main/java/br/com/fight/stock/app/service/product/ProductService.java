package br.com.fight.stock.app.service.product;

import br.com.fight.stock.app.controller.product.dto.request.ProductRequest;
import br.com.fight.stock.app.controller.product.dto.response.ProductResponse;
import br.com.fight.stock.app.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product updateProduct(Long id, ProductRequest productRequest);
    String archiveProduct(Long id);
    List<ProductResponse> getProductWithDate(Integer day, Integer month, Integer year);
    List<Product> getProductWithSpecification(Boolean published, Boolean featured, Boolean promotion);
    String insertImage(Long id, MultipartFile file) throws IOException;
    ProductResponse createProduct(ProductRequest productRequest);
}
