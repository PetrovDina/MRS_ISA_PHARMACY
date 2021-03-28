package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.repository.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	public Location findOne(Long id) {
		return locationRepository.findById(id).orElse(null);
	}
	
	public List<Location> findAll(){
		return locationRepository.findAll();
	}

	public Page<Location> findAll(Pageable page) {
		return locationRepository.findAll(page);
	}
	
	public Location save(Location location) {
		return locationRepository.save(location);
	}
	
	public void remove(Long id) {
		locationRepository.deleteById(id);
	}
	
	public List<Location> findAllByCity(String city) {
		return locationRepository.findAllByCity(city);
	}
	
	public List<Location> findByCityAllIgnoringCase(String city) {
		return locationRepository.findByCityAllIgnoringCase(city);
	}
	
}
