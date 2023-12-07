package ecommerce.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.models.OrderModel;
import ecommerce.models.ViewOrdersModel;
import ecommerce.services.OrderService;

@RestController
public class OrderController {
	@Autowired private OrderService orderService;
	
	@GetMapping("AddToCart")
	public String addToCart(@RequestParam("quantity") String quantity,@RequestParam("productId") long productId,Principal principal) {
		return orderService.addToCart(principal.getName(),quantity,productId);
		
	}
	
	@GetMapping("ViewOrders")
	public List<ViewOrdersModel> viewOrders(Principal principal,@RequestParam("type") String type){
		System.out.println("XNINVINIWNINVIWNV**************VSNVINEIVNEIJVIE");
		System.out.println(principal.getName()+ "  "+ type);
		return orderService.viewOrders(principal.getName(),type);
	}
	
	@GetMapping("OrderNow")
	public String orderNow(@RequestParam("orderId") long orderId) {
		return orderService.orderNow(orderId);
	}
	@GetMapping("DispatchOrder")
	public String dispatchOrder (@RequestParam("orderId") long orderId) {
		return orderService.dispatchOrder(orderId);
	}
	
	@GetMapping("Delivered")
	public String delivered(@RequestParam("orderId") long orderId) {
		return orderService.delivered(orderId);
	}

}
