package ecommerce.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.models.DiscountModel;
import ecommerce.models.MerchantModel;
import ecommerce.models.OrderModel;
import ecommerce.repositories.DiscountRepository;
import ecommerce.repositories.MerchantReposirtory;
import ecommerce.repositories.OrderRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DiscountService {
	@Autowired private DiscountRepository discountRepository;
	@Autowired private MerchantReposirtory merchantReposirtory;
	@Autowired private OrderRepository orderRepository;

	public String addDiscount(String name, DiscountModel discountModel) {
		MerchantModel merchantModel = merchantReposirtory.findByEmail(name);
		discountModel.setMerchantModel(merchantModel);
		discountRepository.save(discountModel);
		return "Discount Added";
	}

	public List<DiscountModel> viewDiscounts(String name) {
		MerchantModel  merchantModel = merchantReposirtory.findByEmail(name);
		List<DiscountModel> discountModelsList = discountRepository.findByMerchantModel(merchantModel);
		return discountModelsList;
	}

	public List<DiscountModel> discountValidation(String enteredCoupon, long orderId) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		MerchantModel merchantModel = orderModel.getMerchantModel();
		List<DiscountModel> discountModelsList = discountRepository.findByMerchantModelAndCouponCodeAndExpiryDateAfterOrExpiryDate(merchantModel,enteredCoupon,new Date(),new Date());
		System.out.println(discountModelsList.size());
		if(discountModelsList.size()>0) {
			return discountModelsList;
		}else {
			return discountModelsList;
		}
		
	}

}
