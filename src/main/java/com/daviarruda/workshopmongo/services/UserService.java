package com.daviarruda.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daviarruda.workshopmongo.domain.User;
import com.daviarruda.workshopmongo.repository.UserRepository;
import com.daviarruda.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll(){
		return repo.findAll();
	}
	public User findById(String id) {
		Optional<User> user = repo.findById(id);
		  return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

}
