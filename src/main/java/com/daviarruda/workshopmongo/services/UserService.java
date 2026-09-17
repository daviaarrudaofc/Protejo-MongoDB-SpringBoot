package com.daviarruda.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daviarruda.workshopmongo.domain.User;
import com.daviarruda.workshopmongo.dto.UserDTO;
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

	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	public User update(User obj) {
		Optional<User> obj1= repo.findById(obj.getId());
		User newObj = obj1.orElseThrow(() -> new ObjectNotFoundException("objeto nao encontrado"));
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	public User fromDTO(UserDTO objDTO) {
		return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail());
	}
	
	
}
