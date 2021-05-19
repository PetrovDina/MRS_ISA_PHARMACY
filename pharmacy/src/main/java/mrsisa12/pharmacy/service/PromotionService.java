package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Promotion;
import mrsisa12.pharmacy.repository.PromotionRepository;

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;

	public Promotion findOne(Long id) {
		return promotionRepository.findById(id).orElse(null);
	}

	public List<Promotion> findAll() {
		return promotionRepository.findAll();
	}

	public Page<Promotion> findAll(Pageable page) {
		return promotionRepository.findAll(page);
	}

	public Promotion save(Promotion promotion) {
		return promotionRepository.save(promotion);
	}

	@Transactional(readOnly = false)
	public void deletePromotion(Promotion promotion) {
		promotionRepository.delete(promotion);
	}

	public List<Promotion> findAllWithPromotionItems() {
		return promotionRepository.findAllWithPromotionItems();
	}

	public Promotion findOneWithPromotionItems(Long id) {
		return promotionRepository.findOneWithPromotionItems(id);
	}
}
