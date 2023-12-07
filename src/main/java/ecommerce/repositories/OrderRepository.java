package ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.CustomerModel;
import ecommerce.models.MerchantModel;
import ecommerce.models.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

	List<OrderModel> findByMerchantModelAndCustomerModelAndStatus(MerchantModel merchantModel,
			CustomerModel customerModel, String string);

	List<OrderModel> findByCustomerModelAndStatus(CustomerModel customerModel, String string);

	List<OrderModel> findByMerchantModelAndStatus(MerchantModel merchantModel, String string);

	List<OrderModel> findByCustomerModelAndStatusIn(CustomerModel customerModel, List<String> statusList);

	long countByCustomerModelEmailAndStatus(String email, String status);

}
