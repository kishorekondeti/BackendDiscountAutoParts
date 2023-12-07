package ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ecommerce.models.OrderItemModel;
import ecommerce.models.OrderModel;
import ecommerce.repositories.OrderItemRepository;
import ecommerce.repositories.OrderRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderItemService {
	@Autowired private OrderItemRepository orderItemRepository;
	@Autowired private OrderRepository orderRepository;

	public String removeProduct(long orderItemId, long orderId) {
		OrderItemModel orderItemModel = orderItemRepository.findById(orderItemId).get();
		OrderModel orderModel = orderItemModel.getOrderModel();
		orderItemRepository.delete(orderItemModel);
		List<OrderItemModel> orderItemModelsList = orderItemRepository.findByOrderModel(orderModel);
		if(orderItemModelsList.size() == 0) {
			orderRepository.delete(orderModel);
		}
		return "Product Removed";
	}

}
