package ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.services.OrderItemService;

@RestController
public class OrderItemController {
	@Autowired private OrderItemService orderItemService;
	
	@GetMapping("RemoveProduct")
	public String removeProduct(@RequestParam("orderId") long orderId,@RequestParam("orderItemId") long orderItemId) {
		return orderItemService.removeProduct(orderItemId,orderId);
	}

}
