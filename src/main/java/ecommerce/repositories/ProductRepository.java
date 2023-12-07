package ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecommerce.models.BrandModel;
import ecommerce.models.MerchantModel;
import ecommerce.models.ProductModel;
import ecommerce.models.SubCategoryModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

	List<ProductModel> findByMerchantModel(MerchantModel merchantModel);

	List<ProductModel> findByMerchantModelAndProductNameLike(MerchantModel merchantModel, String string);

	List<ProductModel> findByMerchantModelAndBrandModel(MerchantModel merchantModel, BrandModel brandModel);

	List<ProductModel> findByMerchantModelAndProductNameLikeAndSubCategoryModel(MerchantModel merchantModel,
			String string, SubCategoryModel subCategoryModel);

	List<ProductModel> findByMerchantModelAndProductNameLikeAndSubCategoryModelIn(MerchantModel merchantModel,
			String string, List<SubCategoryModel> subCategoryModelList);

	List<ProductModel> findByMerchantModelAndProductNameLikeAndBrandModel(MerchantModel merchantModel, String string,
			BrandModel brandModel);

	List<ProductModel> findByMerchantModelAndProductNameLikeAndBrandModelAndSubCategoryModel(
			MerchantModel merchantModel, String string, BrandModel brandModel, SubCategoryModel subCategoryModel);

	List<ProductModel> findByMerchantModelAndProductNameLikeAndBrandModelAndSubCategoryModelIn(
			MerchantModel merchantModel, String string, BrandModel brandModel,
			List<SubCategoryModel> subCategoryModelList);

	List<ProductModel> findByProductNameLikeAndBrandModelAndSubCategoryModel(String string, BrandModel brandModel,
			SubCategoryModel subCategoryModel);

	List<ProductModel> findByProductNameLikeAndBrandModelAndSubCategoryModelIn(String string, BrandModel brandModel,
			List<SubCategoryModel> subCategoryModelList);


	List<ProductModel> findByProductNameLikeAndSubCategoryModel(String string, SubCategoryModel subCategoryModel);

	List<ProductModel> findByProductNameLikeAndBrandModel(String string, BrandModel brandModel);

	List<ProductModel> findByProductNameLikeAndSubCategoryModelIn(String string,
			List<SubCategoryModel> subCategoryModelList);

	List<ProductModel> findByProductNameLike(String string);

	



}
