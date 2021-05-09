package mrsisa12.pharmacy.dto.order;

import java.time.LocalDate;

import mrsisa12.pharmacy.dto.PharmacyAdminDTO;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.enums.OrderStatus;

public class OrderDTO {

	private Long id;
	private String dueDate;
	private PharmacyAdminDTO pharmacyAdmin;
	private OrderStatus orderStatus;

	public OrderDTO() {
	}

	public OrderDTO(Long id, LocalDate dueDate, PharmacyAdminDTO pharmacyAdmin, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.dueDate = (dueDate == null) ? null : dueDate.toString();
		this.pharmacyAdmin = pharmacyAdmin;
		this.orderStatus = orderStatus;
	}

	public OrderDTO(Order order) {
		this(order.getId(), order.getDueDate(), new PharmacyAdminDTO(order.getPharmacyAdmin()), order.getStatus());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public PharmacyAdminDTO getPharmacyAdmin() {
		return pharmacyAdmin;
	}

	public void setPharmacyAdmin(PharmacyAdminDTO pharmacyAdmin) {
		this.pharmacyAdmin = pharmacyAdmin;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
