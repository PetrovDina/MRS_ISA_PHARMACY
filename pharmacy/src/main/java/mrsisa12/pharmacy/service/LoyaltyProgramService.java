package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.LoyaltyProgram;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.PatientCategory;
import mrsisa12.pharmacy.repository.LoyaltyProgramRepository;

@Service
public class LoyaltyProgramService {

	@Autowired
	private LoyaltyProgramRepository loyaltyProgramRepository;
	
	@SuppressWarnings("deprecation")
	public LoyaltyProgram getLoyaltyProgram()
	{
		return loyaltyProgramRepository.findOneById(new Long(1));
	}
	
	public LoyaltyProgram save(LoyaltyProgram loyaltyProgram)
	{
		return loyaltyProgramRepository.save(loyaltyProgram);
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
	
}
