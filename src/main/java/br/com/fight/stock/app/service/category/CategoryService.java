package br.com.fight.stock.app.service.category;

import br.com.fight.stock.app.controller.categories.dto.request.CategoriesRequest;
import br.com.fight.stock.app.domain.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    String deleteCategory(Long id);
    Category updateCategory(CategoriesRequest categoriesRequest, String nameCategory);
    String insertImage(MultipartFile file, String nameCategory) throws IOException;
    Category insertProductInCategory(String nameCategory, Long idProduct);
    Category createCategory(Category category);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
}
