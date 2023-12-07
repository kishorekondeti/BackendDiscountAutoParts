package ecommerce.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ecommerce.models.ProductModel;
import ecommerce.services.ProductService;

@RestController
public class ProductController {
	@Autowired private ProductService productService;
	@Value("${productPicture}")
	String productPicture;
	
	@RequestMapping(value = "AddProduct", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addProduct (
			@RequestParam(name="picture") MultipartFile multipartFile,
			@RequestParam String productName,
			@RequestParam float price,
			@RequestParam String about,
			@RequestParam long subCategoryId,
			@RequestParam long brandId,
			Principal principal
			)
	{
		try {
			File uploadedFile = new File(productPicture, multipartFile.getOriginalFilename());
			uploadedFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(uploadedFile);
			fos.write(multipartFile.getBytes());
			fos.close();
			
			ProductModel productModel = new ProductModel();
			productModel.setAbout(about);
			productModel.setPicture(multipartFile.getOriginalFilename());
			productModel.setPrice(price);
			productModel.setProductName(productName);
			return productService.addProduct(productModel,brandId,subCategoryId,principal.getName());
		} catch (Exception e) {
			System.out.println(e);
			return "Fail to upload";
		}
	}
	
	@GetMapping("ViewProducts")
	public List<ProductModel> viewProducts(Principal principal,@RequestParam("searchKeyword") String searchKeyword,@RequestParam("categoryId") String categoryId,@RequestParam("subCategoryId") String subCategoryId,@RequestParam("brandId") String brandId){
		return productService.viewProducts(principal.getName(),brandId,searchKeyword,categoryId,subCategoryId);
	}
	

}
