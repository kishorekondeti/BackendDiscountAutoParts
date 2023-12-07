package ecommerce.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.models.UsersModel;
import ecommerce.services.UsersService;


@RestController
public class LoginCredentailController{
	@Autowired
	private UsersService loginService;
	@PostMapping("AdminLogin")
	public ResponseEntity<String> adminLogin(@RequestBody UsersModel loginCredentialsModel) {
		System.out.println(loginCredentialsModel.getEmail());
		return loginService.loginAction(loginCredentialsModel);
	}

	@PostMapping("MerchantLogin")
	public ResponseEntity<String> merchantLogin(@RequestBody UsersModel loginCredentialsModel) {
		System.out.println(loginCredentialsModel.getEmail());
		return loginService.loginAction(loginCredentialsModel);
	}
	@PostMapping("CustomerLogin")
	public ResponseEntity<String> customerLogin(@RequestBody UsersModel loginCredentialsModel) {
		System.out.println(loginCredentialsModel.getEmail());
		return loginService.loginAction(loginCredentialsModel);
	}
	
	
	
	

}
