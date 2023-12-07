package ecommerce.models;

import java.util.List;



public class ViewOrdersModel {
	private OrderModel orderModel;
	private List<OrderItemModel> orderItemModels;

	public void setOrderModel(OrderModel orderModel) {
		this.orderModel = orderModel;
	}
	public OrderModel getOrderModel() {
		return orderModel;
	}

	public List<OrderItemModel> getOrderItemModels() {
		return orderItemModels;
	}
	public void setOrderItemModels(List<OrderItemModel> orderItemModels) {
		this.orderItemModels = orderItemModels;
	}
	

}
