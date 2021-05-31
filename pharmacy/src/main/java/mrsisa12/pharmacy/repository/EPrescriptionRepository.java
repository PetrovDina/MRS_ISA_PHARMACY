package mrsisa12.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.EPrescription;

public interface EPrescriptionRepository extends JpaRepository<EPrescription, Long> {

}
