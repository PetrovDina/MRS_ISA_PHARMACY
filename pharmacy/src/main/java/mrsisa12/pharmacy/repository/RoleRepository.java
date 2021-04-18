package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
	List<UserRole> findByName(String name);
}
