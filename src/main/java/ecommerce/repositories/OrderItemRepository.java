package ecommerce.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.OrderItemModel;
import ecommerce.models.OrderModel;
import ecommerce.models.ProductModel;

public interface OrderItemRepository extends JpaRepository<OrderItemModel, Long> {

	OrderItemModel findByOrderModelAndProductModel(OrderModel orderModel, ProductModel productModel);

	List<OrderItemModel> findByOrderModel(OrderModel orderModel);


}
