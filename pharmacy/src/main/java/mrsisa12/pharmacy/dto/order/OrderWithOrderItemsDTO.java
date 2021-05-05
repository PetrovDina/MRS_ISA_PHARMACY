package mrsisa12.pharmacy.dto.order;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.dto.OrderItemDTO;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;

public class OrderWithOrderItemsDTO extends OrderDTO {

	private List<OrderItemDTO> orderItems;

	public OrderWithOrderItemsDTO() {}

	public OrderWithOrderItemsDTO(Order order) {
		super(order);
		fillOrderItems(order.getOrderItems());
	}

	private void fillOrderItems(List<OrderItem> orderItems) {
		if (this.orderItems == null)
			this.orderItems = new ArrayList<OrderItemDTO>();
		for (OrderItem oItem : orderItems)
			this.orderItems.add(new OrderItemDTO(oItem));
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}
}
