package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PromotionDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemWithItemPricesDTO;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Promotion;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.service.ItemPriceService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
import mrsisa12.pharmacy.service.PromotionService;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;
	
	@Autowired
	private ItemPriceService itemPriceService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<PromotionDTO>> getAllPromotions() {

		List<Promotion> promotions = promotionService.findAllWithPromotionItems();

		List<PromotionDTO> promotionsDTO = new ArrayList<PromotionDTO>();
		for (Promotion promotion : promotions) {
			for (PharmacyStorageItem pharmacyStorageItem : promotion.getPharmacyStorageItems()) {
				PharmacyStorageItem psiWithItemPrices = pharmacyStorageItemService.findOneWithItemPrices(pharmacyStorageItem.getId());
				pharmacyStorageItem.setItemPrices(psiWithItemPrices.getItemPrices());
			}
			promotionsDTO.add(new PromotionDTO(promotion));
		}

		return new ResponseEntity<>(promotionsDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PromotionDTO>> getPromotionsPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<Promotion> promotions = promotionService.findAll(page);

		// convert promotions to DTOs
		List<PromotionDTO> promotionsDTO = new ArrayList<>();
		for (Promotion e : promotions) {
			promotionsDTO.add(new PromotionDTO(e));
		}

		return new ResponseEntity<>(promotionsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PromotionDTO> getPromotion(@PathVariable Long id) {

		Promotion promotion = promotionService.findOneWithPromotionItems(id);
		// promotion must exist
		if (promotion == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		for (PharmacyStorageItem pharmacyStorageItem : promotion.getPharmacyStorageItems()) {
			PharmacyStorageItem psiWithItemPrices = pharmacyStorageItemService.findOneWithItemPrices(pharmacyStorageItem.getId());
			pharmacyStorageItem.setItemPrices(psiWithItemPrices.getItemPrices());
		}

		return new ResponseEntity<>(new PromotionDTO(promotion), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<PromotionDTO> savePromotion(@RequestBody PromotionDTO promotionDTO) {
		Promotion promotion = new Promotion();
		
		LocalDate localDateNow = LocalDate.now();
		LocalTime localTime = LocalTime.parse("00:00:00");
		
		promotion.setDueDate(LocalDate.parse(promotionDTO.getDueDate()));
		
		for (PharmacyStorageItemWithItemPricesDTO psi : promotionDTO.getPromotionItems()) {
			// ovdje onu sto je true postavljam na false
			PharmacyStorageItem psiFromBase = pharmacyStorageItemService.findOneWithItemPrices(psi.getId());
			for (ItemPrice ip : psiFromBase.getItemPrices()) {
				if (ip.isCurrent()) {
					ip.setCurrent(false);
					ip.getTimePeriod().setEndDate(localDateNow);
					ip.getTimePeriod().setEndTime(localTime);
					itemPriceService.save(ip);
				}
			}
			// kreiramo trenutni datum i vrijeme za datum i vrijeme kreiranja, datum i vrijeme isteka je null
			TimePeriod timePeriod = new TimePeriod(localDateNow, localTime, LocalDate.parse(promotionDTO.getDueDate()), localTime);
			// pravim novi ItemPrice koji ce biti trenutna cijena
			ItemPrice itemPrice = new ItemPrice(psi.getItemPrices().get(0).getPrice(), true, true, timePeriod);
			itemPrice.setPharmacyStorageItem(psiFromBase);
			// cuvamo itemPrice
			itemPriceService.save(itemPrice);
			psiFromBase.addItemPrice(itemPrice);
			pharmacyStorageItemService.save(psiFromBase);
		}
		promotion.setDeleted(false);
		// cuvamo promociju
		promotion = promotionService.save(promotion);
		
		for (PharmacyStorageItemDTO psi : promotionDTO.getPromotionItems()) {
			// ovdje onu sto je true postavljam na false
			PharmacyStorageItem psiFromBase = pharmacyStorageItemService.findOneWithItemPrices(psi.getId());
			psiFromBase.setPromotion(promotion);
			pharmacyStorageItemService.save(psiFromBase);
		}
		
		// setujem da saljem na front
		promotion = promotionService.findOneWithPromotionItems(promotion.getId());
		
		for (PharmacyStorageItem pharmacyStorageItem : promotion.getPharmacyStorageItems()) {
			PharmacyStorageItem psiWithItemPrices = pharmacyStorageItemService.findOneWithItemPrices(pharmacyStorageItem.getId());
			pharmacyStorageItem.setItemPrices(psiWithItemPrices.getItemPrices());
		}
		
		return new ResponseEntity<>(new PromotionDTO(promotion), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<PromotionDTO> updatePromotion(@RequestBody PromotionDTO promotionDTO) {

		// an promotion must exist
		Promotion promotion = promotionService.findOne(promotionDTO.getId());

		if (promotion == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		promotion = promotionService.save(promotion);
		return new ResponseEntity<>(new PromotionDTO(promotion), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {

		Promotion promotion = promotionService.findOne(id);

		if (promotion != null) {
			promotionService.deletePromotion(promotion);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}

