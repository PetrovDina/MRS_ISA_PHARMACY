package mrsisa12.pharmacy.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.repository.InMemoryMedicationRepository;

@Service
public class MedicationService implements IMedicationService{

	@Autowired
	private InMemoryMedicationRepository medicationRepository;

	@Override
	public Collection<Medication> findAll() {
		Collection<Medication> medications = medicationRepository.findAll();
		return medications;
	}

	@Override
	public Medication findOne(Integer id) {
		Medication medication = medicationRepository.findOne(id);
		return medication;
	}

	@Override
	public Medication create(Medication medication) throws Exception {
		if (medication.getId() != null) {
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Medication savedMedication = medicationRepository.create(medication);
		return savedMedication;
	}

	@Override
	public Medication update(Medication medication) throws Exception {
		Medication medicationToUpdate = findOne(medication.getId());
		if (medicationToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		medicationToUpdate.setMedicationName(medication.getMedicationName());
		Medication updatedMedication = medicationRepository.create(medicationToUpdate);
		return updatedMedication;
	}

	@Override
	public void delete(Integer id) {
		medicationRepository.delete(id);
	}
}
