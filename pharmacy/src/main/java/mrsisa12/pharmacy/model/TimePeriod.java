package mrsisa12.pharmacy.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class TimePeriod implements Serializable {

	/**
	 * Klasa koja reprezentuje period u vremenu, od kad do kad vazi neka cijena
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Timestamp startTimestamp;
	private Timestamp endTimestamp;
	//private Date datum;
	
	public TimePeriod() {
		
	}
	
	public TimePeriod(Timestamp startTimestamp, Timestamp endTimestamp) {
		super();
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
	}



	public Timestamp getStartTimestamp() {
		return startTimestamp;
	}
	public void setStartTimestamp(Timestamp startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
	public Timestamp getEndTimestamp() {
		return endTimestamp;
	}
	public void setEndTimestamp(Timestamp endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
	
	
}
