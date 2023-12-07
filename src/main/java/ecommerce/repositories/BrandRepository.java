package ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.BrandModel;

public interface BrandRepository extends JpaRepository<BrandModel, Long> {

	List<BrandModel> findByBrandName(String brandName);

}
