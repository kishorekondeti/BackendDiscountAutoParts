package ecommerce.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ecommerce.models.BrandModel;
import ecommerce.models.CategoryModel;
import ecommerce.models.MerchantModel;
import ecommerce.models.ProductModel;
import ecommerce.models.SubCategoryModel;
import ecommerce.models.UsersModel;
import ecommerce.repositories.BrandRepository;
import ecommerce.repositories.CategoryRepository;
import ecommerce.repositories.CustomerRepository;
import ecommerce.repositories.LoginRepository;
import ecommerce.repositories.MerchantReposirtory;
import ecommerce.repositories.ProductRepository;
import ecommerce.repositories.RatingRepository;
import ecommerce.repositories.SubCategoryRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	@Autowired private ProductRepository productRepository;
	@Autowired private BrandRepository brandRepository;
	@Autowired private SubCategoryRepository subCategoryRepository;
	@Autowired private MerchantReposirtory merchantReposirtory;
	@Autowired private CategoryRepository categoryRepository;
	@Autowired private LoginRepository loginRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private RatingRepository ratingRepository;
	@Value("${productPicture}")
	String productPicture;

	public String addProduct(ProductModel productModel, long brandId, long subCategoryId,String email) {
		MerchantModel merchantModel = merchantReposirtory.findByEmail(email);
		BrandModel brandModel = brandRepository.findById(brandId).get();
		SubCategoryModel subCategoryModel = subCategoryRepository.findById(subCategoryId).get();
		productModel.setSubCategoryModel(subCategoryModel);
		productModel.setBrandModel(brandModel);
		productModel.setMerchantModel(merchantModel);
		productRepository.save(productModel);
		return "Product Added Successfully";
	}

	public List<ProductModel> viewProducts(String name,String brandId,String searchKeyword,String categoryId,String subCategoryId) {
		UsersModel usersModel = loginRepository.findByEmail(name);
		List<ProductModel> productModelsList = new ArrayList<ProductModel>();
		if(usersModel.getRole().equalsIgnoreCase("ROLE_MERCHANT")) {
			MerchantModel merchantModel = merchantReposirtory.findByEmail(name);
			if(brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				productModelsList = productRepository.findByMerchantModelAndProductNameLike(merchantModel,"%"+searchKeyword+"%");
			}else if(brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByMerchantModelAndProductNameLikeAndSubCategoryModel(merchantModel,"%"+searchKeyword+"%",subCategoryModel);
			}else if(brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				CategoryModel categoryModel  = categoryRepository.findById(Long.parseLong(categoryId)).get();
				List<SubCategoryModel> subCategoryModelList = subCategoryRepository.findByCategoryModel(categoryModel);
				productModelsList = productRepository.findByMerchantModelAndProductNameLikeAndSubCategoryModelIn(merchantModel,"%"+searchKeyword+"%",subCategoryModelList);
			}else if(brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByMerchantModelAndProductNameLikeAndSubCategoryModel(merchantModel,"%"+searchKeyword+"%",subCategoryModel);
			}else if(!brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				productModelsList = productRepository.findByMerchantModelAndProductNameLikeAndBrandModel(merchantModel,"%"+searchKeyword+"%",brandModel);
			}else if(!brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByMerchantModelAndProductNameLikeAndBrandModelAndSubCategoryModel(merchantModel,"%"+searchKeyword+"%",brandModel,subCategoryModel);
			}else if(!brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				CategoryModel categoryModel  = categoryRepository.findById(Long.parseLong(categoryId)).get();
				List<SubCategoryModel> subCategoryModelList = subCategoryRepository.findByCategoryModel(categoryModel);
				productModelsList = productRepository.findByMerchantModelAndProductNameLikeAndBrandModelAndSubCategoryModelIn(merchantModel,"%"+searchKeyword+"%",brandModel,subCategoryModelList);
			}else if(!brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByMerchantModelAndProductNameLikeAndBrandModelAndSubCategoryModel(merchantModel,"%"+searchKeyword+"%",brandModel,subCategoryModel);
			}
		}else if(usersModel.getRole().equalsIgnoreCase("ROLE_CUSTOMER")) {
			if(brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				productModelsList = productRepository.findByProductNameLike("%"+searchKeyword+"%");
			}else if(brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByProductNameLikeAndSubCategoryModel("%"+searchKeyword+"%",subCategoryModel);
			}else if(brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				CategoryModel categoryModel  = categoryRepository.findById(Long.parseLong(categoryId)).get();
				List<SubCategoryModel> subCategoryModelList = subCategoryRepository.findByCategoryModel(categoryModel);
				productModelsList = productRepository.findByProductNameLikeAndSubCategoryModelIn("%"+searchKeyword+"%",subCategoryModelList);
			}else if(brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByProductNameLikeAndSubCategoryModel("%"+searchKeyword+"%",subCategoryModel);
			}else if(!brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				productModelsList = productRepository.findByProductNameLikeAndBrandModel("%"+searchKeyword+"%",brandModel);
			}else if(!brandId.equalsIgnoreCase("") && categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByProductNameLikeAndBrandModelAndSubCategoryModel("%"+searchKeyword+"%",brandModel,subCategoryModel);
			}else if(!brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				CategoryModel categoryModel  = categoryRepository.findById(Long.parseLong(categoryId)).get();
				List<SubCategoryModel> subCategoryModelList = subCategoryRepository.findByCategoryModel(categoryModel);
				productModelsList = productRepository.findByProductNameLikeAndBrandModelAndSubCategoryModelIn("%"+searchKeyword+"%",brandModel,subCategoryModelList);
			}else if(!brandId.equalsIgnoreCase("") && !categoryId.equalsIgnoreCase("")&& !subCategoryId.equalsIgnoreCase("")) {
				BrandModel brandModel = brandRepository.findById(Long.parseLong(brandId)).get();
				SubCategoryModel subCategoryModel = subCategoryRepository.findById(Long.parseLong(subCategoryId)).get();
				productModelsList = productRepository.findByProductNameLikeAndBrandModelAndSubCategoryModel("%"+searchKeyword+"%",brandModel,subCategoryModel);
			}
		}
		
		List<ProductModel> productModels2 = new ArrayList<ProductModel>();
		Iterator<ProductModel> iterator = productModelsList.iterator();
		while(iterator.hasNext()) {
			ProductModel productModel = (ProductModel) iterator.next();
			String rating  = ratingRepository.getRating(productModel.getProductId());
			if(rating==null) {
				productModel.setRating(0);
			}else {
				productModel.setRating(Double.parseDouble(rating));
			}
			System.out.println(rating);
			try {
				 File file=new File(productPicture+"/"+productModel.getPicture());
				 InputStream in = new FileInputStream(file);
				 productModel.setPicture2(Base64.getEncoder().encodeToString(IOUtils.toByteArray(in)));
				 } catch (Exception e) {
				 System.out.println(e);
				 }
			productModels2.add(productModel);
		}
		
		return productModelsList;
	}

}
