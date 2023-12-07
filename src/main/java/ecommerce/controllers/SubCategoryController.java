package ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.models.SubCategoryModel;
import ecommerce.services.SubCategoryService;

@RestController
public class SubCategoryController {
	@Autowired private SubCategoryService subCategoryService;
	
	@PostMapping("AddSubCategory")
	public String addSubCategory(@RequestBody SubCategoryModel subCategoryModel,@RequestParam("categoryId") long categoryId) {
		System.out.println(subCategoryModel);
		System.out.println(categoryId);
		return subCategoryService.addSubCategory(categoryId,subCategoryModel);
	}
	
	@GetMapping("ViewSubCategories")
	public List<SubCategoryModel> viewSubCategories(@RequestParam("categoryId") String categoryId){
		return subCategoryService.viewSubCategories(categoryId);
	}
	@GetMapping("GetSubCategories")
	public List<SubCategoryModel> getSubCategories(){
		return subCategoryService.getSubCategories();
	}

}
