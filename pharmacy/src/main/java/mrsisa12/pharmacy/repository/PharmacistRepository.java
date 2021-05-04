package mrsisa12.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

}
