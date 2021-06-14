package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.LoyaltyProgramDTO;
import mrsisa12.pharmacy.model.LoyaltyProgram;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.PatientCategory;
import mrsisa12.pharmacy.repository.LoyaltyProgramRepository;

@Service
public class LoyaltyProgramService {

	@Autowired
	private LoyaltyProgramRepository loyaltyProgramRepository;
	
	@Autowired
	private PatientService patientService;
	
	@SuppressWarnings("deprecation")
	public LoyaltyProgram getLoyaltyProgram()
	{
		return loyaltyProgramRepository.findOneById(new Long(1));
	}
	
	public LoyaltyProgram save(LoyaltyProgram loyaltyProgram)
	{
		return loyaltyProgramRepository.save(loyaltyProgram);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public LoyaltyProgram updateLoyaltyProgram(LoyaltyProgramDTO loyaltyProgramDTO)
	{
		LoyaltyProgram loyaltyProgram = this.getLoyaltyProgram();
		
		if(isSomeFiledInvalid(loyaltyProgram, loyaltyProgramDTO))
		{
			throw new IllegalArgumentException();
		}
		
		loyaltyProgram.setAfterAppointment(loyaltyProgramDTO.getAfterAppointment());
		loyaltyProgram.setMaxPointsRegular(loyaltyProgramDTO.getMaxPointsRegular());
		loyaltyProgram.setMaxPointsSilver(loyaltyProgramDTO.getMaxPointsSilver());
		loyaltyProgram.setSilverDis(loyaltyProgramDTO.getSilverDis());
		loyaltyProgram.setGoldDis(loyaltyProgramDTO.getGoldDis());
		
		this.save(loyaltyProgram);
		
		patientService.updateCategories(loyaltyProgram.getMaxPointsRegular(), loyaltyProgram.getMaxPointsSilver());
		
		return loyaltyProgram;
	}
	
	public boolean isSomeFiledInvalid(LoyaltyProgram loyaltyProgram, LoyaltyProgramDTO loyaltyProgramDTO)
	{	
		if(loyaltyProgram.getAfterAppointment().compareTo(loyaltyProgramDTO.getPrevAfterAppointment()) != 0)
			return true;

		if(loyaltyProgram.getMaxPointsRegular().compareTo(loyaltyProgramDTO.getPrevMaxPointsRegular()) != 0)
			return true;
		
		if(loyaltyProgram.getMaxPointsSilver().compareTo(loyaltyProgramDTO.getPrevMaxPointsSilver()) != 0)
			return true;
		
		if(loyaltyProgram.getSilverDis().compareTo(loyaltyProgramDTO.getSilverDis()) != 0)
			return true;

		if(loyaltyProgram.getGoldDis().compareTo(loyaltyProgramDTO.getPrevGoldDis()) != 0)
			return true;

		return false;
	}
	
	
	
	public Integer getDiscount(Patient patient)
	{
		if(patient.getCategory() == PatientCategory.SILVER) return this.getLoyaltyProgram().getSilverDis();
		if(patient.getCategory() == PatientCategory.GOLD) return this.getLoyaltyProgram().getGoldDis();
		return 0;
	}
	
	public Double getFinalPrice(Double medicationPrice, Patient patient)
	{
		Integer discount = this.getDiscount(patient);
		Double finalPrice = medicationPrice - (medicationPrice * (((double)discount) / 100));
		return finalPrice;
	}
	
	public Double getFinalAppointmentPrice(Double appointmentPrice, Patient patient)
	{
		Integer discount = this.getDiscount(patient);
		Double finalPrice = appointmentPrice - (appointmentPrice * (((double)discount) / 100));
		return finalPrice;
	}
	
	
	
	public PatientCategory getPatientCategory(Patient patient)
	{
		LoyaltyProgram l = this.getLoyaltyProgram();
		Integer loyaltyPoints = patient.getLoyaltyPoints();
		
		if(loyaltyPoints > l.getMaxPointsRegular() && loyaltyPoints <= l.getMaxPointsSilver())
		{
			return PatientCategory.SILVER;
		}
		
		if(loyaltyPoints > l.getMaxPointsSilver())
		{
			return PatientCategory.GOLD;
		}
		
		return PatientCategory.REGULAR;
	}
	
	public String generateMessage(Patient patient, Double finalPrice, Integer pointsEarned)
	{
		String message = "Medication/s successfully bought with the price of: " + finalPrice + ",00 RSD.";
		if(patient.getCategory() != PatientCategory.REGULAR)
		{
			message += " You have gained a discount of " + this.getDiscount(patient) + "% for each medication because of your "
					+ "loyalty program category. ";
		}
		message += "Loyalty points earned with this purchase: " + pointsEarned + ".";
		
		return message;
	}
	
	public String generateReservationMessage(Patient patient, Double finalPrice, Integer pointsEarned)
	{
		String message = "Medication successfully reserved with the price of: " + finalPrice + " dinars.";
		if(patient.getCategory() != PatientCategory.REGULAR)
		{
			message += " You have gained discount of " + this.getDiscount(patient) + "% for each instance of medication because of your "
					+ "loyalty program category. ";
		}
		message += "Loyalty points earned with this purchase: " + pointsEarned + ".";
		
		return message;
	}
	
	public String generateAppointmentMessage(Patient patient, Double finalPrice, Integer pointsEarned)
	{
		String message = "Appointment successfully booked by price: " + finalPrice + " dinars.";
		if(patient.getCategory() != PatientCategory.REGULAR)
		{
			message += " You have discount of " + this.getDiscount(patient) + "% because of your "
					+ "loyalty program cateogry. ";
		}
		message += "Loyalty points earned with this appointment bookage: " + pointsEarned + ".";
		
		return message;
	}
	
	public Integer appointmentPoints()
	{
		return this.getLoyaltyProgram().getAfterAppointment();
	}
	
}
