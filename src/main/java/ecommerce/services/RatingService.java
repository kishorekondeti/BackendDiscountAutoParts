package ecommerce.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.models.CustomerModel;
import ecommerce.models.OrderItemModel;
import ecommerce.models.ProductModel;
import ecommerce.models.RatingModel;
import ecommerce.repositories.CustomerRepository;
import ecommerce.repositories.OrderItemRepository;
import ecommerce.repositories.ProductRepository;
import ecommerce.repositories.RatingRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RatingService {
	@Autowired private RatingRepository ratingRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderItemRepository orderItemRepository;

	public String ratingForProduct(String rating, long productId, String review,String name,long orderItemId) {
		ProductModel productModel = productRepository.findById(productId).get();
		CustomerModel customerModel = customerRepository.findByEmail(name);
		OrderItemModel orderItemModel = orderItemRepository.findById(orderItemId).get();
		orderItemModel.setStatus("Rating Given");
		RatingModel ratingModel = new RatingModel();
		ratingModel.setDate(new Date());
		ratingModel.setRating(rating);
		ratingModel.setReview(review);
		ratingModel.setCustomerModel(customerModel);
		ratingModel.setOrderItemModel(orderItemModel);
		ratingRepository.save(ratingModel);
		return "Your Response Submitted";
	}

	public List<RatingModel> getRatings(long productId) {
		List<RatingModel> ratingModelsList = ratingRepository.getRatings(productId);
		return ratingModelsList;
	}

}
