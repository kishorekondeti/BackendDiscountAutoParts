package ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.models.CategoryModel;
import ecommerce.repositories.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {
	@Autowired CategoryRepository categoryRepository;

	public String addCategory(CategoryModel categoryModel) {
		List<CategoryModel> categoryModelsList = categoryRepository.findByCategoryName(categoryModel.getCategoryName());
		if(categoryModelsList.size()>0) {
			return "Category Exists";
		}else {
			categoryRepository.save(categoryModel);
			return "Category Added";
		}
		
	}

	public List<CategoryModel> viewCategories() {
		List<CategoryModel> categoryModelsList = categoryRepository.findAll();
		return categoryModelsList;
	}

}
