package ecommerce.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.DiscountModel;
import ecommerce.models.MerchantModel;

public interface DiscountRepository extends JpaRepository<DiscountModel, Long> {

	List<DiscountModel> findByMerchantModel(MerchantModel merchantModel);

	List<DiscountModel> findByMerchantModelAndCouponCode(MerchantModel merchantModel, String enteredCoupon);

	List<DiscountModel> findByMerchantModelAndCouponCodeAndExpiryDateAfterOrExpiryDate(MerchantModel merchantModel,
			String enteredCoupon, Date date, Date date2);

}
