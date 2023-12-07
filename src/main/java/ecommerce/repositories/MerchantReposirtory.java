package ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.MerchantModel;

public interface MerchantReposirtory extends JpaRepository<MerchantModel, Long> {

	List<MerchantModel> findByEmailOrPhone(String email, String phone);

	MerchantModel findByEmail(String email);


}
