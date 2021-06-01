package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.Patient;

public interface EPrescriptionRepository extends JpaRepository<EPrescription, Long> {

	EPrescription findOneByCode(String code);

	List<EPrescription> findAllByPatient(Patient patient);

}
