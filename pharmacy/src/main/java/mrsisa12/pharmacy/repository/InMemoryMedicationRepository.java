package mrsisa12.pharmacy.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import mrsisa12.pharmacy.model.Medication;

@Repository
public class InMemoryMedicationRepository implements MedicationRepository {

	private static AtomicInteger counter = new AtomicInteger();

	private final ConcurrentMap<Integer, Medication> medications = new ConcurrentHashMap<Integer, Medication>();

	
	@Override
	public Collection<Medication> findAll() {
		return this.medications.values();
	}

	@Override
	public Medication create(Medication medication) {
		Integer id = medication.getId();

		if (id == null) {
			id = counter.incrementAndGet();
			medication.setId(id);
		}

		this.medications.put(id, medication);
		return medication;
	}

	@Override
	public Medication findOne(Integer id) {
		return this.medications.get(id);
	}

	@Override
	public Medication update(Medication medication) {
		Integer id = medication.getId();

		if (id != null) {
			this.medications.put(id, medication);
		}

		return medication;
	}

	@Override
	public void delete(Integer id) {
		this.medications.remove(id);
		
	}

}
