package ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.models.BrandModel;
import ecommerce.repositories.BrandRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BrandService {
	@Autowired private BrandRepository brandRepository;

	public String addBrand(BrandModel brandModel) {
		List<BrandModel> brandModelsList = brandRepository.findByBrandName(brandModel.getBrandName());
		if(brandModelsList.size()>0) {
			return brandModel.getBrandName()+ " Exists";
		}else {
			brandRepository.save(brandModel);
			return "Added Successfully";
		}
		
	}

	public List<BrandModel> viewBrands() {
		List<BrandModel> brandModelsList = brandRepository.findAll();
		return brandModelsList;
	}

}
