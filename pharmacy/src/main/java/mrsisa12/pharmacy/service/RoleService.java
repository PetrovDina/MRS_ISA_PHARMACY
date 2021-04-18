package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.UserRole;
import mrsisa12.pharmacy.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public UserRole findById(Long id) {
		UserRole auth = this.roleRepository.getOne(id);
		return auth;
	}

	public List<UserRole> findByName(String name) {
		List<UserRole> roles = this.roleRepository.findByName(name);
		return roles;
	}

}
