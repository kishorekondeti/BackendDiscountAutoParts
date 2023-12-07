package ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.CategoryModel;
import ecommerce.models.SubCategoryModel;

public interface SubCategoryRepository extends JpaRepository<SubCategoryModel, Long> {

	List<SubCategoryModel> findBySubCategoryName(String subCategoryName);

	List<SubCategoryModel> findByCategoryModel(CategoryModel categoryModel);

}
