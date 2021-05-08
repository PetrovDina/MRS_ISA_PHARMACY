package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.OrderItem;

public class OrderItemDTO {

	private Long id;
	private int quantity;
	private MedicationDTO medication;
	// private OrderDTO order;

	public OrderItemDTO() {
	}

	public OrderItemDTO(Long id, int quantity, MedicationDTO medication) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.medication = medication;
	}

	public OrderItemDTO(OrderItem orderItem) {
		this(orderItem.getId(), orderItem.getQuantity(), new MedicationDTO(orderItem.getMedication()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public MedicationDTO getMedication() {
		return medication;
	}

	public void setMedication(MedicationDTO medication) {
		this.medication = medication;
	}
}
