package ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.models.CategoryModel;
import ecommerce.services.CategoryService;

@RestController
public class CategoryController {
	@Autowired private CategoryService categoryService;
	
	@PostMapping("AddCategory")
	public String addCategory(@RequestBody CategoryModel categoryModel) {
		return categoryService.addCategory(categoryModel);
	}
		
	@GetMapping("ViewCategories")
	public List<CategoryModel>  viewCategories(){
		return categoryService.viewCategories();
	}

}
