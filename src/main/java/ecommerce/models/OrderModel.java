package ecommerce.models;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="orders")
public class OrderModel {
	@GeneratedValue
	@Id
    private long orderId;
    private String status;
    private LocalDate orderedDate;
    private float totalPrice;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="customerId")
	private CustomerModel customerModel;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="merchantId")
	private MerchantModel merchantModel;
	@Transient
	private List<OrderItemModel> orderItemModels;
	
	
	public List<OrderItemModel> getOrderItemModels() {
		return orderItemModels;
	}
	public void setOrderItemModels(List<OrderItemModel> orderItemModels) {
		this.orderItemModels = orderItemModels;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getOrderedDate() {
		return orderedDate;
	}
	public void setOrderedDate(LocalDate date) {
		this.orderedDate = date;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public CustomerModel getCustomerModel() {
		return customerModel;
	}
	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}
	public MerchantModel getMerchantModel() {
		return merchantModel;
	}
	public void setMerchantModel(MerchantModel merchantModel) {
		this.merchantModel = merchantModel;
	}
	
	
	
	
}
