package ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.models.MerchantModel;
import ecommerce.services.MerchantService;

@RestController
public class MerchantController {
	@Autowired private MerchantService merchantService;
	
	@PostMapping("AddMerchant")
	public String  addMerchant(@RequestBody MerchantModel merchantModel) {
		return merchantService.addMerchant(merchantModel);
	}
	@GetMapping("ViewMerchants")
	public List<MerchantModel> viewMerchants(){
		return merchantService.viewMerchants();
	}
			

}
