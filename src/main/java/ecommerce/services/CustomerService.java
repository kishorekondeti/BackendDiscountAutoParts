package ecommerce.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ecommerce.models.CustomerModel;
import ecommerce.models.UsersModel;
import ecommerce.repositories.CustomerRepository;
import ecommerce.repositories.LoginRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
	@Autowired private CustomerRepository customerRepository;
	@Autowired private LoginRepository loginRepository;
	
	public String CustomerRegistration(CustomerModel customerModel, Principal principal) {
		List<CustomerModel> customerModelsList = customerRepository.findByEmailOrPhone(customerModel.getEmail(),customerModel.getPhone());
		if(!customerModelsList.isEmpty()) {
			return "Duplicate Customer Details";
		}
		
//		List<UsersModel> credentialsModels = loginRepository.findAll();
//		if(credentialsModels.size()==0) {
//			UsersModel adminModel = new UsersModel();
//			adminModel.setEmail("admin@gmail.com");
//			adminModel.setPassword(new BCryptPasswordEncoder().encode("admin"));
//			adminModel.setRole("ROLE_ADMIN");
//			loginRepository.save(adminModel);
//		}
		
		
		
		UsersModel credentialsModel = new UsersModel();
		credentialsModel.setEmail(customerModel.getEmail());
		credentialsModel.setPassword(new BCryptPasswordEncoder().encode(customerModel.getPassword()));
		credentialsModel.setRole("ROLE_CUSTOMER");
		loginRepository.save(credentialsModel);
		
		UsersModel userModel = loginRepository.findByEmail(customerModel.getEmail());
	    customerModel.setLoginCredentialsModel(userModel);
		customerRepository.save(customerModel);
		return "Customer Registered Successfully";
	}
	

}
