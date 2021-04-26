package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.TimePeriod;

public class TimePeriodDTO {

	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	
	public TimePeriodDTO() {}

	public TimePeriodDTO(String startDate, String startTime, String endDate, String endTime) {
		super();
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
	}

	public TimePeriodDTO(TimePeriod timePeriod) {
		this((timePeriod.getStartDate() == null) ? null : timePeriod.getStartDate().toString(), 
			(timePeriod.getStartTime() == null) ? null : timePeriod.getStartTime().toString(), 
			(timePeriod.getEndDate() == null) ? null : timePeriod.getEndDate().toString(), 
			(timePeriod.getEndTime() == null) ? null : timePeriod.getEndTime().toString());
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
