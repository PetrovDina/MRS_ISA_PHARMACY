package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Rating;
import mrsisa12.pharmacy.repository.RatingRepository;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	public Rating findOne(Long id) {
		return ratingRepository.findById(id).orElseGet(null);
	}

	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}
}
