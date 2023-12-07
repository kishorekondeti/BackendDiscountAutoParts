package ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.models.CategoryModel;
import ecommerce.models.SubCategoryModel;
import ecommerce.repositories.CategoryRepository;
import ecommerce.repositories.SubCategoryRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubCategoryService {
	@Autowired private SubCategoryRepository subCategoryRepository;
	@Autowired private CategoryRepository categoryRepository;

	public String addSubCategory(long categoryId, SubCategoryModel subCategoryModel) {
		List<SubCategoryModel> subCategoryModelsList = subCategoryRepository.findBySubCategoryName(subCategoryModel.getSubCategoryName());
		if(subCategoryModelsList.size()>0) {
			return subCategoryModel.getSubCategoryName()+ " Exists";
		}else {
			CategoryModel categoryModel = categoryRepository.findById(categoryId).get();
			subCategoryModel.setCategoryModel(categoryModel);
			subCategoryRepository.save(subCategoryModel);
			return "Sub Category Added";
		}
		
	}

	public List<SubCategoryModel> viewSubCategories(String categoryId) {
		List<SubCategoryModel> subCategoryModelsList = new ArrayList<SubCategoryModel>();
		if(categoryId.equalsIgnoreCase("")) {
			subCategoryModelsList = subCategoryRepository.findAll();
		}else {
			CategoryModel categoryModel = categoryRepository.findById(Long.parseLong(categoryId)).get();
			subCategoryModelsList = subCategoryRepository.findByCategoryModel(categoryModel);
		}
		return subCategoryModelsList;
	}

	public List<SubCategoryModel> getSubCategories() {
		List<SubCategoryModel> subCategoryModelsList = subCategoryRepository.findAll();
		return subCategoryModelsList; 
	}

	

}
