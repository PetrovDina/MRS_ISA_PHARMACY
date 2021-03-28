package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.repository.ItemPriceRepository;

@Service
public class ItemPriceService {

	@Autowired
	private ItemPriceRepository itemPriceRepository;
	
	public ItemPrice findOne(Long id) {
		return itemPriceRepository.findById(id).orElseGet(null);
	}
	
	public List<ItemPrice> findAll() {
		return itemPriceRepository.findAll();
	}
	
	public Page<ItemPrice> findAll(Pageable page) {
		return itemPriceRepository.findAll(page);
	}
	
	public ItemPrice save(ItemPrice itemPrice) {
		return itemPriceRepository.save(itemPrice);
	}

	public void remove(Long id) {
		itemPriceRepository.deleteById(id);
	}
	
	public List<ItemPrice> findAllCurrent(Boolean current) {
		return itemPriceRepository.findAllCurrent(current);
	}
}
