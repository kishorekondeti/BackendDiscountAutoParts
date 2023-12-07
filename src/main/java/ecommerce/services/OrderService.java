package ecommerce.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import ecommerce.models.CustomerModel;
import ecommerce.models.MerchantModel;
import ecommerce.models.OrderItemModel;
import ecommerce.models.OrderModel;
import ecommerce.models.ProductModel;
import ecommerce.models.UsersModel;
import ecommerce.models.ViewOrdersModel;
import ecommerce.repositories.CustomerRepository;
import ecommerce.repositories.LoginRepository;
import ecommerce.repositories.MerchantReposirtory;
import ecommerce.repositories.OrderItemRepository;
import ecommerce.repositories.OrderRepository;
import ecommerce.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class OrderService {
	@Autowired private OrderRepository orderRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderItemRepository orderItemRepository;
	@Autowired private LoginRepository loginRepository;
	@Autowired private MerchantReposirtory merchantReposirtory;
	@Value("${productPicture}")
	String productPicture;

	public String addToCart(String name, String quantity, long productId) {
		ProductModel productModel = productRepository.findById(productId).get();
		MerchantModel merchantModel = productModel.getMerchantModel();
        CustomerModel customerModel = customerRepository.findByEmail(name);
        List<OrderModel> orderModelsList = orderRepository.findByMerchantModelAndCustomerModelAndStatus(merchantModel,customerModel,"cart");

		OrderModel orderModel = new OrderModel();
        if(orderModelsList.size()==0) {
			orderModel.setStatus("cart");
			orderModel.setOrderedDate(LocalDate.now());
			orderModel.setCustomerModel(customerModel);
			orderModel.setMerchantModel(merchantModel);
			orderRepository.save(orderModel);

		}else {
			orderModel = orderModelsList.get(0);
		}
        OrderItemModel orderItemModel = orderItemRepository.findByOrderModelAndProductModel(orderModel,productModel);
        if(orderItemModel==null) {
			orderItemModel = new OrderItemModel();
			orderItemModel.setPrice(productModel.getPrice());
			orderItemModel.setOrderModel(orderModel);
			orderItemModel.setProductModel(productModel);
			orderItemModel.setQuantity(Integer.parseInt(quantity));
			orderItemRepository.save(orderItemModel);
			return "Added To Cart";
        }else {
			orderItemModel.setQuantity(orderItemModel.getQuantity()+Integer.parseInt(quantity));
			orderItemRepository.saveAndFlush(orderItemModel);
			return "Cart Updated";
        }

	}

	public List<ViewOrdersModel> viewOrders(String name, String type) {
		String customerStatus = "delivered";
		UsersModel usersModel = loginRepository.findByEmail(name);
		System.out.println("*****USERSMODEL*****"+usersModel);
		List<ViewOrdersModel> viewOrdersModelsList = new ArrayList<ViewOrdersModel>();
		List<OrderModel> orderModelsList = new ArrayList<OrderModel>();
		if(usersModel.getRole().equalsIgnoreCase("ROLE_CUSTOMER")) {
			CustomerModel customerModel = customerRepository.findByEmail(name);
			System.out.println("*****CUSTOMEMODEL*****"+customerModel);
			if(type.equalsIgnoreCase("cart")) {
				System.out.println("*****INSIDECARTCONDITION*****");
				orderModelsList = orderRepository.findByCustomerModelAndStatus(customerModel,"cart");
				System.out.println("*****ORDERMODELSLIST*****"+orderModelsList);
			}else if(type.equalsIgnoreCase("ordered")) {
				System.out.println("*****INSIDEORDEREDCONDITION*****");
				List<String> statusList = new ArrayList<>();
				statusList.add("ordered");
				statusList.add("dispatched");
				orderModelsList = orderRepository.findByCustomerModelAndStatusIn(customerModel, statusList);
			}else if(type.equalsIgnoreCase("history")) {
				System.out.println("*****INSIDEHISTORYCONDITION*****");
				orderModelsList = orderRepository.findByCustomerModelAndStatus(customerModel,"delivered");
			}
		}else if(usersModel.getRole().equalsIgnoreCase("ROLE_MERCHANT")) {
			MerchantModel merchantModel = merchantReposirtory.findByEmail(name);
			if(type.equalsIgnoreCase("ordered")) {
				orderModelsList = orderRepository.findByMerchantModelAndStatus(merchantModel,"ordered");
			}else if(type.equalsIgnoreCase("dispatched")) {
				orderModelsList = orderRepository.findByMerchantModelAndStatus(merchantModel, "dispatched");
			}else if(type.equalsIgnoreCase("history")) {
				orderModelsList = orderRepository.findByMerchantModelAndStatus(merchantModel, "delivered");
			}
		}
		Iterator<OrderModel> orderModelsListIterator = orderModelsList.iterator();
		while(orderModelsListIterator.hasNext()) {
			OrderModel orderModel = orderModelsListIterator.next();
			List<OrderItemModel> orderItemModelsList = orderItemRepository.findByOrderModel(orderModel);
			ViewOrdersModel viewOrdersModel = new ViewOrdersModel();
			viewOrdersModel.setOrderModel(orderModel);
			List<OrderItemModel> orderItemModelsList2 = new ArrayList<OrderItemModel>();
			Iterator<OrderItemModel> orderItemModelsListIterator = orderItemModelsList.iterator();
			float totalPrice = 0;
			while(orderItemModelsListIterator.hasNext()) {
				OrderItemModel orderItemModel = orderItemModelsListIterator.next();
				ProductModel productModel = orderItemModel.getProductModel();
				try {
					File file=new File(productPicture+"/"+productModel.getPicture());
					InputStream in = new FileInputStream(file);
					productModel.setPicture2(Base64.getEncoder().encodeToString(IOUtils.toByteArray(in)));
					float price = productModel.getPrice();
					int quantity = orderItemModel.getQuantity();

					if(orderRepository.countByCustomerModelEmailAndStatus(name,customerStatus) ==0){
						totalPrice = totalPrice+(quantity * price);
						float firstTimeOrderDiscount = (float) (totalPrice*0.05);
						totalPrice = totalPrice-firstTimeOrderDiscount;
						orderModel.setTotalPrice(totalPrice);
						orderItemModel.setProductModel(productModel);
						orderItemModelsList2.add(orderItemModel);
					} else{
						totalPrice = totalPrice+quantity * price;
						orderModel.setTotalPrice(totalPrice);
						orderItemModel.setProductModel(productModel);
						orderItemModelsList2.add(orderItemModel);
					}

				} catch (Exception e) {
					System.out.println(e);
				}
			}
			viewOrdersModel.setOrderItemModels(orderItemModelsList2);
			viewOrdersModelsList.add(viewOrdersModel);
		}

		System.out.println("*****VIEWORDERSMODELLIST*****"+viewOrdersModelsList);

		return viewOrdersModelsList;
	}

	public String orderNow(long orderId) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		List<OrderItemModel> orderItemModelsList = orderItemRepository.findByOrderModel(orderModel);
		Iterator<OrderItemModel> iterator = orderItemModelsList.iterator();
		while(iterator.hasNext()) {
			OrderItemModel orderItemModel = iterator.next();
			orderItemModel.setStatus("Ordered");
			orderItemRepository.saveAndFlush(orderItemModel);
		}
		orderModel.setStatus("ordered");
		orderRepository.saveAndFlush(orderModel);
		return "Order Placed";
	}

	public String dispatchOrder(long orderId) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		orderModel.setStatus("dispatched");
		orderRepository.saveAndFlush(orderModel);
		return "Order Dispatched";
	}

	public String delivered(long orderId) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		orderModel.setStatus("delivered");
		orderRepository.saveAndFlush(orderModel);
		return "Order Received";
	}


}
