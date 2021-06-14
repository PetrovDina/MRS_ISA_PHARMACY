package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemWithItemPricesDTO;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.repository.PharmacyStorageItemRepository;

@Service
public class PharmacyStorageItemService {

	@Autowired
	private PharmacyStorageItemRepository pharmacyStorageItemRepository;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private MedicationService medicationService;
	

	@Autowired
	private ItemPriceService itemPriceService;

	public PharmacyStorageItem findOne(Long id) {
		return pharmacyStorageItemRepository.findById(id).orElseGet(null);
	}

	public List<PharmacyStorageItem> findAll() {
		return pharmacyStorageItemRepository.findAll();
	}

	public Page<PharmacyStorageItem> findAll(Pageable page) {
		return pharmacyStorageItemRepository.findAll(page);
	}

	public PharmacyStorageItem save(PharmacyStorageItem pharmacyStorageItem) {
		return pharmacyStorageItemRepository.save(pharmacyStorageItem);
	}

	@Transactional(readOnly = false)
	public void deletePharmacyStorageItem(PharmacyStorageItem pharmacyStorageItem) {
		pharmacyStorageItemRepository.delete(pharmacyStorageItem);
	}
	
	public void updatePharmacyStorageItemQuantity(Medication medication, Pharmacy pharmacy, int quantity) 
	{
		PharmacyStorageItem psi = pharmacyStorageItemRepository.findOneWithMedicationAndPharmacy(medication.getId(), pharmacy.getId());
		// povecavamo kolicinu za porucenu kolicinu - mozes da prosledis i negativan broj da bi smanjio kolicinu
		psi.setQuantity(psi.getQuantity() + quantity);
		pharmacyStorageItemRepository.save(psi);
	}
	
	public void updatePharmacyStorageItemCounter(Medication medication, Pharmacy pharmacy) 
	{
		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemRepository.findOneWithMedicationAndPharmacy(medication.getId(), pharmacy.getId());
		// kada dostavljac dostavio lijek, counter koji oznacava broj trazenja lijeka se stavlja na 0, jer vise quantity nije 0
		pharmacyStorageItem.setCounter(0);
		pharmacyStorageItemRepository.save(pharmacyStorageItem);
	}

	public PharmacyStorageItem findOneWithItemPrices(Long pharmacyStorageItemId) {
		return pharmacyStorageItemRepository.findOneWithItemPrices(pharmacyStorageItemId);
	}
	
	public List<PharmacyStorageItem> findAllWithCurrentPriceByMedication(Long medicationId) {
		return pharmacyStorageItemRepository.findAllWithCurrentPriceByMedication(medicationId);
	}

	public PharmacyStorageItem findOneWithMedication(Long pharmacyStorageItemId) {
		return pharmacyStorageItemRepository.findOneWithMedication(pharmacyStorageItemId);
	}
	
	public PharmacyStorageItem findOneWithMedicationAndPharmacy(Long medicationId, Long pharmacyId) {
		return pharmacyStorageItemRepository.findOneWithMedicationAndPharmacy(medicationId, pharmacyId);
	}

	@Transactional(readOnly = false)
	public void restoreDeletedPharmacyStorageItem(Long id) {
		pharmacyStorageItemRepository.restoreById(id);
	}
	
	public Double getCurrentPrice(PharmacyStorageItem item)
	{
		for(ItemPrice price : item.getItemPrices())
		{
			if(price.isCurrent())
				return price.getPrice();
		}
		
		return 0.0;
	}

	public List<PharmacyStorageItem> findAllOutOfStock(Long pharmacyId) {
		return pharmacyStorageItemRepository.findAllOutOfStock(pharmacyId);
	}

	public PharmacyStorageItem createPharmacyStorageItem(
			PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWIPDTO) {
		
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(pharmacyStorageItemWIPDTO.getPharmacy().getId());
		Medication medication = medicationService.findOne(pharmacyStorageItemWIPDTO.getMedication().getId());
		// kreiramo trenutni datum i vrijeme za datum i vrijeme kreiranja, datum i vrijeme isteka je null
		LocalDate localDateNow = LocalDate.now();
		LocalTime localTimeNow = LocalTime.now();
		TimePeriod timePeriod = new TimePeriod(localDateNow, localTimeNow, null, null);
		// potrebno je kreirati novu cijenu
		ItemPrice itemPrice = new ItemPrice(pharmacyStorageItemWIPDTO.getItemPrices().get(0).getPrice(), true, false, timePeriod);
		PharmacyStorageItem pharmacyStorageItem = new PharmacyStorageItem();
		// postavljamo pharmacyStorageItem itemPrice-u
		itemPrice.setPharmacyStorageItem(pharmacyStorageItem);
		// dodajemo cijenu
		pharmacyStorageItem.addItemPrice(itemPrice);
		// dodajemo pokazivac na lijek
		pharmacyStorageItem.setMedication(medication);
		// dodajemo kolicinu
		pharmacyStorageItem.setQuantity(pharmacyStorageItemWIPDTO.getQuantity());
		// postoje je kreiranje onda je deleted na false
		pharmacyStorageItem.setDeleted(false);
		// dodajemo apotkue novom pharmacyStorageItem-u
		pharmacyStorageItem.setPharmacy(pharmacy);
		pharmacyStorageItem.setCounter(0);

		return save(pharmacyStorageItem);
		
	}

	public PharmacyStorageItem updatePharmacyStorageItem(PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWIPDTO) {
		
		PharmacyStorageItem pharmacyStorageItem = findOneWithItemPrices(pharmacyStorageItemWIPDTO.getId());
		
		LocalDate localDateNow = LocalDate.now();
		LocalTime localTimeNow = LocalTime.now();
		// ovdje onu sto je true postavljam na false
		setCurrentItemPrice(pharmacyStorageItem, localDateNow, localTimeNow);
		// kreiramo trenutni datum i vrijeme za datum i vrijeme kreiranja, datum i vrijeme isteka je null
		TimePeriod timePeriod = new TimePeriod(localDateNow, localTimeNow, null, null);
		// pravim novi ItemPrice koji ce biti trenutna cijena
		ItemPrice itemPrice = new ItemPrice(pharmacyStorageItemWIPDTO.getItemPrices().get(0).getPrice(), true, false, timePeriod);
		// dodajemo itemPrice-u referencu na pharmacyStorageItem
		itemPrice.setPharmacyStorageItem(pharmacyStorageItem);
		// cuvamo itemPrice
		itemPriceService.save(itemPrice);
		
		pharmacyStorageItem.addItemPrice(itemPrice); // vracam update-ovan lijek - odnosno lijek koji sada ima novu cijenu
		
		return pharmacyStorageItem;
	}

	private void setCurrentItemPrice(PharmacyStorageItem pharmacyStorageItem, LocalDate localDateNow,
			LocalTime localTimeNow) {
		for (ItemPrice ip : pharmacyStorageItem.getItemPrices()) {
			if (ip.isCurrent()) {
				ip.setCurrent(false);
				ip.setPromotion(false);
				ip.getTimePeriod().setEndDate(localDateNow);
				ip.getTimePeriod().setEndTime(localTimeNow);
				itemPriceService.save(ip);
			}
		}
	}

}
