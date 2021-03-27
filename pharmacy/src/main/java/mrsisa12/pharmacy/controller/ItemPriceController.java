package mrsisa12.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.ItemPriceDTO;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.service.ItemPriceService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;

@RestController
@RequestMapping("/itemPrice")
public class ItemPriceController {
	
	@Autowired
	private ItemPriceService itemPriceService;
	
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ItemPriceDTO> createItemPrice(@RequestBody ItemPriceDTO itemPriceDTO) {

		if (itemPriceDTO.getPharmacyStorageItem()==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOneWithItemPrices(itemPriceDTO.getPharmacyStorageItem().getId());
		
		if (pharmacyStorageItem == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ItemPrice itemPrice = new ItemPrice();
		itemPrice.setCurrent(itemPriceDTO.isCurrent());
		itemPrice.setPrice(itemPriceDTO.getPrice());
		itemPrice.setPharmacyStorageItem(pharmacyStorageItem);
		pharmacyStorageItem.addItemPrice(itemPrice);
		
		itemPrice = itemPriceService.save(itemPrice);
		return new ResponseEntity<>(new ItemPriceDTO(itemPrice), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<ItemPriceDTO> updateItemPrice(@RequestBody ItemPriceDTO itemPriceDTO) {

		ItemPrice itemPrice = itemPriceService.findOne(itemPriceDTO.getId());
		if (itemPrice == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		itemPrice.setCurrent(itemPriceDTO.isCurrent());

		itemPrice = itemPriceService.save(itemPrice);
		return new ResponseEntity<>(new ItemPriceDTO(itemPrice), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteItemPrice(@PathVariable Long id) {

		ItemPrice itemPrice = itemPriceService.findOne(id);

		if (itemPrice != null) {
			itemPriceService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
