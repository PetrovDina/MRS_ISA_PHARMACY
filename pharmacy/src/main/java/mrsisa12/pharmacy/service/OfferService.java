package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.repository.OfferRepository;

@Service
public class OfferService {
	
	@Autowired
	private OfferRepository offerRepository;
	
	public Offer findOne(Long id) {
		return offerRepository.findById(id).orElse(null);
	}

	public List<Offer> findAll() {
		return offerRepository.findAll();
	}
	
	public Page<Offer> findAll(Pageable page) {
		return offerRepository.findAll(page);
	}
	
	public Offer save(Offer offer) {
		return offerRepository.save(offer);
	}
	
	public List<Offer> findAllOfSupplier(Supplier supplier)
	{
		return offerRepository.findAllOfSupplier(supplier);
	}

}
