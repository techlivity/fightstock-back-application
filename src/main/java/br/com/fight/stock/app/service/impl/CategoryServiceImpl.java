package br.com.fight.stock.app.service.impl;

import br.com.fight.stock.app.controller.categories.dto.request.CategoriesRequest;
import br.com.fight.stock.app.domain.Category;
import br.com.fight.stock.app.domain.Image;
import br.com.fight.stock.app.domain.Product;
import br.com.fight.stock.app.exceptions.CategorieNotFoundException;
import br.com.fight.stock.app.exceptions.NotFoundCategoryException;
import br.com.fight.stock.app.exceptions.ProductNotFoundException;
import br.com.fight.stock.app.repository.categories.CategoriesRepository;
import br.com.fight.stock.app.repository.products.ProductsRepository;
import br.com.fight.stock.app.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;
    private final ProductsRepository productsRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository, ProductsRepository productsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public String deleteCategory(Long id) {
        categoriesRepository.deleteById(id);
        return "Sucessfully deleted!";
    }

    @Override
    public Category updateCategory(CategoriesRequest categoriesRequest, String nameCategory) {
        return categoriesRepository.findByName(nameCategory).map(categoryMap ->
                        categoriesRepository.save(Category.convertCategoriesRequestToCategory(categoriesRequest, categoryMap)))
                .orElseThrow(() -> new CategorieNotFoundException("Categories is not found!!!"));
    }

    @Override
    public String insertImage(MultipartFile file, String nameCategory) throws IOException {
        Category category = categoriesRepository.findByName(nameCategory)
                .orElseThrow(() -> new CategorieNotFoundException("Categories is not found!!!"));
        category.setImage(Image.createImage(file));
        categoriesRepository.save(category);
        return "Upload image Successfully !";
    }

    @Override
    public Category insertProductInCategory(String nameCategory, Long idProduct) {
        Category category = categoriesRepository.findByName(nameCategory)
                .orElseThrow(() -> new CategorieNotFoundException("Categories is not found!!!"));
        Product product = productsRepository.findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException("Product is not found!!!"));
        List<Product> products = category.getProducts();
        products.add(product);
        return categoriesRepository.save(category);
    }

    @Override
    public Category createCategory(Category category) {
        return categoriesRepository.save(category);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoriesRepository.findByName(name)
                .orElseThrow(() -> new NotFoundCategoryException("Categoria não encontrada"));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoriesRepository.findAll();
    }
}
