package mrsisa12.pharmacy.repository;

import java.util.Collection;

import mrsisa12.pharmacy.model.Medication;

public interface MedicationRepository {

	Collection<Medication> findAll();

	Medication create(Medication medication);

	Medication findOne(Integer id);
	
	Medication update(Medication medication);

	void delete(Integer id);
}
