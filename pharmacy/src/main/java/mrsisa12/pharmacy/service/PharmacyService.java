package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.dto.PharmacyWithMedicationsPriceQrDTO;
import mrsisa12.pharmacy.dto.report.ReportDTO;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.repository.PharmacyRepository;
import mrsisa12.pharmacy.repository.ReservationRepository;

@Service
public class PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;

	public Pharmacy findOne(Long id) {
		return pharmacyRepository.findById(id).orElseGet(null);
	}

	public List<Pharmacy> findAll() {
		return pharmacyRepository.findAll();
	}

	public Page<Pharmacy> findAll(Pageable page) {
		return pharmacyRepository.findAll(page);
	}

	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyRepository.save(pharmacy);
	}

	public void remove(Long id) {
		pharmacyRepository.deleteById(id);
	}

	public List<Pharmacy> findAllByName(String name) {
		return pharmacyRepository.findAllByName(name);
	}

	public List<Pharmacy> findByNameAllIgnoringCase(String name) {
		return pharmacyRepository.findByNameAllIgnoringCase(name);
	}

	public Pharmacy findOneWithStorageItems(Long pharmacyId) {
		return pharmacyRepository.findOneWithStorageItems(pharmacyId);
	}

	public Pharmacy findOneByStorageItem(Long storageItemId) {
		return pharmacyRepository.findOneByStorageItem(storageItemId);
	}

	public List<Pharmacy> findByQuery(String query) {
		return pharmacyRepository.findByQuery(query);
	}

	public Pharmacy findOneWithEmployments(Long id) {
		return pharmacyRepository.findOneWithEmployments(id);
	}
	
	public Pharmacy findOneWithPharmacyAdmins(Long id) {
		return pharmacyRepository.findOneWithPharmacyAdmins(id);
	}
	
	public boolean containsPharmacy(List<Pharmacy> pharmacies, Pharmacy pharmacy)
	{
		for (Pharmacy pharm : pharmacies) {
			if(pharm.getId() == pharmacy.getId())
				return true;
		}
		return false;
	}

	public Pharmacy findOneWithSubscribedPatients(Long id) {
		return pharmacyRepository.findOneWithSubscribedPatients(id);
	}
	
	public HashMap<String, Integer> getAllMedicationConsumptedByYear(String year, Pharmacy pharmacy) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		List<Reservation> reservationsFromPharmacy = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		String[] quarters = {"Q1", "Q2", "Q3", "Q4"};
		for (String quarter : quarters) {
			HashMap<String, Integer> dataFromQuarter = getAllMedicationConsumptedByQuarter(quarter, year, pharmacy, reservationsFromPharmacy);
			for(Map.Entry<String, Integer> entry : dataFromQuarter.entrySet()) {
				data.put(entry.getKey(), entry.getValue());
			}
		}
		return data;
	}

	public HashMap<String, Integer> getAllMedicationConsumptedByQuarter(String quarter, String year, Pharmacy pharmacy, List<Reservation> reservations) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		if(reservations == null)
			reservations = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		if(ReportDTO.quarters.containsKey(quarter)) {
			String[] monthsInQuarter = ReportDTO.quarters.get(quarter).split(",");
			for (String month : monthsInQuarter) {
				String monthTrimmed = month.trim();
				data.put(monthTrimmed, 0);
				HashMap<String, Integer> dataFromMonth = getAllMedicationConsumptedByMonthInYear(monthTrimmed, year, pharmacy, reservations);
				for(Map.Entry<String, Integer> entry : dataFromMonth.entrySet()) {
				    data.put(monthTrimmed, data.get(monthTrimmed) + entry.getValue());
				}
			}
		}
		return data;
	}

	public HashMap<String, Integer> getAllMedicationConsumptedByMonthInYear(String period, String year, Pharmacy pharmacy, List<Reservation> reservations) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		LocalDate startDate, endDate;
		
		String month = (ReportDTO.ReportMonths.valueOf(period).ordinal() < 9) ? "0"+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1) : ""+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1);
		LocalDate initial = LocalDate.parse(year+"-"+ month +"-01");
		startDate = initial.withDayOfMonth(1);
		endDate = initial.withDayOfMonth(initial.lengthOfMonth());
		if(reservations == null) 
			reservations = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		
		Calendar start = Calendar.getInstance();
		start.setTime(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		Calendar end = Calendar.getInstance();
		end.setTime(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

		for (LocalDate date = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) {
		    String dayInMonth = String.valueOf(date.getDayOfMonth());
		    data.put(dayInMonth, 0);
		    for (Reservation reservation : reservations) {
				if(date.isEqual(reservation.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
					data.put(dayInMonth, data.get(dayInMonth) + reservation.getQuantity() * 200); // Za sad je svaki lijek 200 din
				}
			}
		}
		return data;
	}
	
	public boolean pharmacyInList(List<PharmacyWithMedicationsPriceQrDTO> pharmacies, Pharmacy pharmacy)
	{
		for (PharmacyWithMedicationsPriceQrDTO p : pharmacies) 
		{
			if(p.getPharmacy().getId() == pharmacy.getId())
				return true;
		}
		
		return false;
	}

}
