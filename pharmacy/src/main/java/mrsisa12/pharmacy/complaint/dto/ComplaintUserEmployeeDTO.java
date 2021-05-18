package mrsisa12.pharmacy.complaint.dto;

import mrsisa12.pharmacy.dto.PlainEmployeeDTO;
import mrsisa12.pharmacy.model.ComplaintEmployee;

public class ComplaintUserEmployeeDTO extends ComplaintUserDTO {
	
	private PlainEmployeeDTO employee;

	public ComplaintUserEmployeeDTO() 
	{
		super();
	}

	public ComplaintUserEmployeeDTO(Long id, String content, PlainEmployeeDTO employee) 
	{
		super(id, content);
		this.employee = employee;
	}

	public ComplaintUserEmployeeDTO(ComplaintEmployee c) 
	{
		this(c.getId(), c.getContent(), new PlainEmployeeDTO(c.getEmployee()));
	}

	public PlainEmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(PlainEmployeeDTO employee) {
		this.employee = employee;
	}

}
