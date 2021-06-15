package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.LoyaltyProgram;

public class LoyaltyProgramDTO {
	
	private Long id;
	private Integer afterAppointment;
	private Integer maxPointsRegular;
	private Integer maxPointsSilver;
	private Integer silverDis;
	private Integer goldDis;
	
	private Integer prevAfterAppointment;
	private Integer prevMaxPointsRegular;
	private Integer prevMaxPointsSilver;
	private Integer prevSilverDis;
	private Integer prevGoldDis;
	
	public LoyaltyProgramDTO() { }
	
	public LoyaltyProgramDTO(Long id, Integer afterAppointment, Integer maxPointsRegular, Integer maxPointsSilver,
			Integer silverDis, Integer goldDis) {
		super();
		this.id = id;
		this.afterAppointment = afterAppointment;
		this.maxPointsRegular = maxPointsRegular;
		this.maxPointsSilver = maxPointsSilver;
		this.silverDis = silverDis;
		this.goldDis = goldDis;
	}
	
	public LoyaltyProgramDTO(LoyaltyProgram loyaltyProgram) {
		this(loyaltyProgram.getId(), loyaltyProgram.getAfterAppointment(), loyaltyProgram.getMaxPointsRegular(),
				loyaltyProgram.getMaxPointsSilver(), loyaltyProgram.getSilverDis(), loyaltyProgram.getGoldDis());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAfterAppointment() {
		return afterAppointment;
	}

	public void setAfterAppointment(Integer afterAppointment) {
		this.afterAppointment = afterAppointment;
	}

	public Integer getMaxPointsRegular() {
		return maxPointsRegular;
	}

	public void setMaxPointsRegular(Integer maxPointsRegular) {
		this.maxPointsRegular = maxPointsRegular;
	}

	public Integer getMaxPointsSilver() {
		return maxPointsSilver;
	}

	public void setMaxPointsSilver(Integer maxPointsSilver) {
		this.maxPointsSilver = maxPointsSilver;
	}

	public Integer getSilverDis() {
		return silverDis;
	}

	public void setSilverDis(Integer silverDis) {
		this.silverDis = silverDis;
	}

	public Integer getGoldDis() {
		return goldDis;
	}

	public void setGoldDis(Integer goldDis) {
		this.goldDis = goldDis;
	}

	public Integer getPrevAfterAppointment() {
		return prevAfterAppointment;
	}

	public void setPrevAfterAppointment(Integer prevAfterAppointment) {
		this.prevAfterAppointment = prevAfterAppointment;
	}

	public Integer getPrevMaxPointsRegular() {
		return prevMaxPointsRegular;
	}

	public void setPrevMaxPointsRegular(Integer prevMaxPointsRegular) {
		this.prevMaxPointsRegular = prevMaxPointsRegular;
	}

	public Integer getPrevMaxPointsSilver() {
		return prevMaxPointsSilver;
	}

	public void setPrevMaxPointsSilver(Integer prevMaxPointsSilver) {
		this.prevMaxPointsSilver = prevMaxPointsSilver;
	}

	public Integer getPrevSilverDis() {
		return prevSilverDis;
	}

	public void setPrevSilverDis(Integer prevSilverDis) {
		this.prevSilverDis = prevSilverDis;
	}

	public Integer getPrevGoldDis() {
		return prevGoldDis;
	}

	public void setPrevGoldDis(Integer prevGoldDis) {
		this.prevGoldDis = prevGoldDis;
	}
	
	

}
