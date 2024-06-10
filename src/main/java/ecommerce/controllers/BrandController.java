package ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.models.BrandModel;
import ecommerce.services.BrandService;

@RestController
public class BrandController {
  @Autowired private BrandService brandService;
  
  @PostMapping("AddBrand")
   public String addBrand(@RequestBody BrandModel brandModel) {
	  System.out.println("ADD A NEW BRAND");
	  return brandService.addBrand(brandModel);
  }
  
  @GetMapping("ViewBrands")
  public List<BrandModel> viewBrands(){
	  return brandService.viewBrands();
  }
  
  
}
