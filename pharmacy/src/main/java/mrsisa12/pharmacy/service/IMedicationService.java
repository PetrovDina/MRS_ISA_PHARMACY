package mrsisa12.pharmacy.service;

import java.util.Collection;

import mrsisa12.pharmacy.model.Medication;

public interface IMedicationService {

	Collection<Medication> findAll();

	Medication findOne(Integer id);

	Medication create(Medication medication) throws Exception;

	Medication update(Medication medication) throws Exception;

	void delete(Integer id);
}
