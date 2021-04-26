package mrsisa12.pharmacy.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import mrsisa12.pharmacy.dto.TimePeriodDTO;

public class TimePeriod implements Serializable {

	/**
	 * Klasa koja reprezentuje period u vremenu, od kad do kad vazi neka cijena
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDate startDate;
	private LocalTime startTime;
	private LocalDate endDate;
	private LocalTime endTime;

	public TimePeriod() {

	}

	public TimePeriod(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
		super();
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
	}

	public TimePeriod(TimePeriodDTO timePeriodDTO) {
		this(LocalDate.parse(timePeriodDTO.getStartDate()), LocalTime.parse(timePeriodDTO.getStartTime()),
				LocalDate.parse(timePeriodDTO.getEndDate()), LocalTime.parse(timePeriodDTO.getEndTime()));
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

}
