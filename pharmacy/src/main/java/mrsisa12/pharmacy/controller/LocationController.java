package mrsisa12.pharmacy.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.LocationDTO;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {
	
	@Autowired
	private LocationService locationService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<LocationDTO>> getAllLocations(){
		
		List<Location> locations = locationService.findAll();
		
		List<LocationDTO> locationsDTO = new ArrayList<LocationDTO>();
		for (Location location : locations) {
			locationsDTO.add(new LocationDTO(location));
		}
		return new ResponseEntity<>(locationsDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<LocationDTO>> getLocationsPage(Pageable page) {

		Page<Location> locations = locationService.findAll(page);

		List<LocationDTO> locationsDTO = new ArrayList<>();
		for (Location m : locations) {
			locationsDTO.add(new LocationDTO(m));
		}

		return new ResponseEntity<>(locationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id){
		
		Location location = locationService.findOne(id);
		
		if(location == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new LocationDTO(location),HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<LocationDTO> saveLocation(@RequestBody LocationDTO locationDTO){
		
		Location location = new Location();
		
		location.setCity(locationDTO.getCity());
		location.setLatitude(locationDTO.getLatitude());
		location.setLongitude(locationDTO.getLongitude());
		location.setStreet(locationDTO.getStreet());
		location.setStreetNum(locationDTO.getStreetNum());
		location.setZipcode(locationDTO.getZipcode());
		
		location = locationService.save(location);
		return new ResponseEntity<>(new LocationDTO(location), HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO){
		
		Location location = locationService.findOne(locationDTO.getId());
		
		if(location == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		location.setCity(locationDTO.getCity());
		location.setLatitude(locationDTO.getLatitude());
		location.setLongitude(locationDTO.getLongitude());
		location.setStreet(locationDTO.getStreet());
		location.setStreetNum(locationDTO.getStreetNum());
		location.setZipcode(locationDTO.getZipcode());

		location = locationService.save(location);
		return new ResponseEntity<>(new LocationDTO(location), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable Long id){
		
		Location location = locationService.findOne(id);
		
		if(location != null) {
			locationService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/findCity")
	public ResponseEntity<List<LocationDTO>> getLocationsByCity(@RequestParam String city){
		
		List<Location> locations = locationService.findAllByCity(city);
		
		List<LocationDTO> locationDTO = new ArrayList<>();
		for(Location l : locations) {
			locationDTO.add(new LocationDTO(l));
		}
		return new ResponseEntity<>(locationDTO, HttpStatus.OK);
	}
	
}
