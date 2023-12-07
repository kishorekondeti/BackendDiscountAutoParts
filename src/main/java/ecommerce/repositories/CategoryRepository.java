package ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

	List<CategoryModel> findByCategoryName(String categoryName);

}
