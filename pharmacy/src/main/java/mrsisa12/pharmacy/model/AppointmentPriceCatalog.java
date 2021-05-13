package mrsisa12.pharmacy.model;

public class AppointmentPriceCatalog {

	private double examinationPrice; // cijena pregleda kod dermatologa
	private double consultationPrice; // cijena pregleda kod farmaceuta
	
	public AppointmentPriceCatalog() {
		
	}
	
	public AppointmentPriceCatalog(double examinationPrice, double consultationPrice) {
		this();
		this.examinationPrice = examinationPrice;
		this.consultationPrice = consultationPrice;
	}

	public double getExaminationPrice() {
		return examinationPrice;
	}
	public void setExaminationPrice(double examinationPrice) {
		this.examinationPrice = examinationPrice;
	}
	public double getConsultationPrice() {
		return consultationPrice;
	}
	public void setConsultationPrice(double consultationPrice) {
		this.consultationPrice = consultationPrice;
	}
	
}
