package mrsisa12.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loyalty_program")
public class LoyaltyProgram {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "afterAppointment", nullable = false)
	private Integer afterAppointment;
	
	@Column(name = "maxPointsRegular", nullable = false)
	private Integer maxPointsRegular;

	@Column(name = "maxPointsSilver", nullable = false)
	private Integer maxPointsSilver;
	
	@Column(name = "silverDis", nullable = false)
	private Integer silverDis;
	
	@Column(name = "goldDis", nullable = false)
	private Integer goldDis;

	public LoyaltyProgram() { } 
	
	public LoyaltyProgram(Long id, Integer afterAppointment, Integer maxPointsRegular, Integer maxPointsSilver,
			Integer silverDis, Integer goldDis) {
		super();
		this.id = id;
		this.afterAppointment = afterAppointment;
		this.maxPointsRegular = maxPointsRegular;
		this.maxPointsSilver = maxPointsSilver;
		this.silverDis = silverDis;
		this.goldDis = goldDis;
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
	
}
