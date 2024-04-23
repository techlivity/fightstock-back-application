package br.com.fight.stock.app.service.impl;

import br.com.fight.stock.app.controller.product.dto.request.ProductRequest;
import br.com.fight.stock.app.controller.product.dto.response.ProductResponse;
import br.com.fight.stock.app.domain.Image;
import br.com.fight.stock.app.domain.Product;
import br.com.fight.stock.app.exceptions.ProductNotFoundException;
import br.com.fight.stock.app.repository.image.ImageRepository;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import br.com.fight.stock.app.service.product.ProductService;
import br.com.fight.stock.app.service.product.core.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final ImageRepository imageRepository;

    public ProductServiceImpl(ProductsRepository productsRepository, ImageRepository imageRepository) {
        this.productsRepository = productsRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.convertProductRequestToProduct(productRequest);
        Product newProduct = productsRepository.save(product);
        return Product.convertProductToProductResponse(newProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        return productsRepository.findById(id).map(product1 ->
                        productsRepository.save(Product.convertProductRequestToProduct(productRequest, product1)))
                .orElseThrow(() -> new ProductNotFoundException("Product not found ! "));
    }

    @Override
    public String archiveProduct(Long id) {
        Product product = productsRepository.findById(id).orElseThrow();
        product.setFiled(true);
        product.setPublished(false);
        return "Product archived successfully !";
    }

    @Override
    public List<Product> getProductWithSpecification(Boolean published, Boolean featured, Boolean promotion) {
        Specification<Product> specification = Specification.where(ProductSpecification.published(published));

        if (featured != null) {
            specification = specification.and(ProductSpecification.featured(featured));
        } else if (promotion != null) {
            specification = specification.and(ProductSpecification.promotion(promotion));
        }

        List<Product> products = productsRepository.findAll(specification);

        if (products.isEmpty())
            throw new ProductNotFoundException("No products found !");

        return products;
    }

    @Override
    public String insertImage(Long id, MultipartFile file) throws IOException {
        Product product = productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found !"));
        Image image = Image.createImage(file);
        imageRepository.save(image);
        product.setImage(image);
        productsRepository.save(product);
        return "Image upload successfully !";
    }

    @Override
    public List<ProductResponse> getProductWithDate(Integer day, Integer month, Integer year) {
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        List<Product> byCreationDate = productsRepository.findByCreationDate(day, month, year);
        return byCreationDate.stream()
                .map(Product::convertProductToProductResponse)
                .toList();
    }
}
