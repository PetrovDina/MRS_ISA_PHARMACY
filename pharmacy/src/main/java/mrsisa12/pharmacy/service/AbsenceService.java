package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Absence;
import mrsisa12.pharmacy.repository.AbsenceRepository;

@Service
public class AbsenceService {

	@Autowired
	private AbsenceRepository absenceRepository;
	
	public Absence findOne(Long id) {
		return absenceRepository.findById(id).orElseGet(null);
	}

	public List<Absence> findAll() {
		return absenceRepository.findAll();
	}

	public Page<Absence> findAll(Pageable page) {
		return absenceRepository.findAll(page);
	}

	public Absence save(Absence absence) {
		return absenceRepository.save(absence);
	}
	
	public List<Absence> findAllByEmployeeId(Long id) {
		return absenceRepository.findAllByEmployeeId(id);
	}

	public List<Absence> findAllByPharmacyId(Long id) {
		return absenceRepository.findAllByPharmacyId(id);
	}
	
	public List<Absence> findAllAprovedAbsencesByEmployeeId(Long id){
		return absenceRepository.findAllAprovedAbsencesByEmployeeId(id);
	}
}
