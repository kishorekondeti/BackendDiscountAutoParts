package ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ecommerce.models.MerchantModel;
import ecommerce.models.UsersModel;
import ecommerce.repositories.LoginRepository;
import ecommerce.repositories.MerchantReposirtory;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MerchantService {
	@Autowired private MerchantReposirtory merchantReposirtory;
	@Autowired private LoginRepository loginRepository;

	public String addMerchant(MerchantModel merchantModel) {
		List<MerchantModel> merchantModelsList = merchantReposirtory.findByEmailOrPhone(merchantModel.getEmail(),merchantModel.getPhone());
		if(merchantModelsList.size()>0) {
			return "Duplicate Merchant Details";
		}
		 UsersModel credentialsModel = new UsersModel();
		credentialsModel.setEmail(merchantModel.getEmail());
		credentialsModel.setPassword(new BCryptPasswordEncoder().encode(merchantModel.getPassword()));
		credentialsModel.setRole("ROLE_MERCHANT");
		loginRepository.save(credentialsModel);
		UsersModel credentialsModel2 = loginRepository.findByEmail(merchantModel.getEmail());
		merchantModel.setLoginCredentialsModel(credentialsModel2);
		merchantReposirtory.save(merchantModel);
		return "Merchant Added Successfully";
	}

	public List<MerchantModel> viewMerchants() {
		List<MerchantModel> merchantModelsList = merchantReposirtory.findAll();
		return merchantModelsList;
	}

}
