package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import mrsisa12.pharmacy.dto.SupplierStorageItemDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.SupplierStorageItem;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.SupplierService;
import mrsisa12.pharmacy.service.SupplierStorageItemService;

@RestController
@RequestMapping("/supplierStorageItem")
public class SupplierStorageItemController {
	
	@Autowired
	private SupplierStorageItemService supplierStorageItemService;

	@Autowired
	private SupplierService supplierService;

	@SuppressWarnings("unused")
	@Autowired
	private MedicationService medicationService;
	
	@GetMapping(value = "/fromSupplier/{username}")
	public ResponseEntity<List<SupplierStorageItemDTO>> getSupplierStorageItemsFromSupplierBySupplierId(@PathVariable String username) 
	{
		Supplier tempSupp = supplierService.findOne(username);
		Supplier supplier = supplierService.findOneWithStorageItems(tempSupp.getId());
		
		List<SupplierStorageItemDTO> supplierStorageItemDTOs = new ArrayList<SupplierStorageItemDTO>();
		
		for (SupplierStorageItem ssi : supplier.getSupplierStorageItems()) 
		{
			SupplierStorageItem temp = supplierStorageItemService.findOneWithMedication(ssi.getId());
			ssi.setMedication(temp.getMedication());
			ssi.setSupplier(supplier);
			supplierStorageItemDTOs.add(new SupplierStorageItemDTO(ssi));
		}

		return new ResponseEntity<>(supplierStorageItemDTOs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<SupplierStorageItemDTO> createSupplierStorageItem(
			@RequestBody SupplierStorageItemDTO supplierStorageItemDTO) {

		Supplier supplier = supplierService.findOneWithStorageItems(supplierStorageItemDTO.getSupplierId());
		Medication medication = medicationService.findOne(supplierStorageItemDTO.getMedication().getId());


		SupplierStorageItem suppleirStorageItem = new SupplierStorageItem();

		suppleirStorageItem.setMedication(medication);
		suppleirStorageItem.setQuantity(supplierStorageItemDTO.getQuantity());
		suppleirStorageItem.setReservedQuantity(0);
		suppleirStorageItem.setDeleted(false);
		suppleirStorageItem.setSupplier(supplier);
		
		supplierStorageItemService.save(suppleirStorageItem);
		
		return new ResponseEntity<>(
				new SupplierStorageItemDTO(supplierStorageItemService.findOne(suppleirStorageItem.getId())),
				HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/update", consumes = "application/json")
	public ResponseEntity<SupplierStorageItemDTO> updateSupplierStorageItem(
			@RequestBody SupplierStorageItemDTO supplierStorageItemDTO) {

		SupplierStorageItem suppleirStorageItem = supplierStorageItemService.findOne(supplierStorageItemDTO.getId());
		if (suppleirStorageItem == null) 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		suppleirStorageItem.setQuantity(supplierStorageItemDTO.getQuantity());
		supplierStorageItemService.save(suppleirStorageItem);
		
		return new ResponseEntity<>(new SupplierStorageItemDTO(suppleirStorageItem), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteSupplierStorageItem(@PathVariable Long id) {

		SupplierStorageItem suppleirStorageItem = supplierStorageItemService.findOne(id);

		if (suppleirStorageItem != null) 
		{
			supplierStorageItemService.deleteSupplierStorageItem(suppleirStorageItem);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
