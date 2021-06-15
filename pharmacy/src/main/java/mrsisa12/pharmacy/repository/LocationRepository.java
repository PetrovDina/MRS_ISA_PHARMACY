package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	public Page<Location> findAll(Pageable pageable);
	
	/*
	 * Pronalazi sve objekte tipa Location i vraca onoliko objekata koliko je
	 * specificirano kroz Pageable objekat. Npr. ako se prosledi objekat: new
	 * PageRequest(0, 10) vratice se nulta stranica sa prvih 10 objekata tipa
	 * Location. Vise informacija na:
	 * http://docs.spring.io/autorepo/docs/spring-data-commons/1.10.0.RC1/api/org/
	 * springframework/data/domain/PageRequest.html
	 */
	
	public List<Location> findAllByCity(String city);
	
	public List<Location> findByCityAllIgnoringCase(String city);

	@Query("select loc from Location loc where loc.latitude =?1 and loc.longitude =?2")
	public Location findOneByLatAndLong(double latitude, double longitude);
		
}
