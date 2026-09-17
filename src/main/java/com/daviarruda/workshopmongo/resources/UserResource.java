package com.daviarruda.workshopmongo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daviarruda.workshopmongo.domain.User;
import com.daviarruda.workshopmongo.dto.UserDTO;
import com.daviarruda.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping    // Diz que este método vai responder requisições HTTP do tipo GET
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		// Converte a lista de User para uma lista de UserDTO.
		// O stream() percorre cada User da lista,
		// o map() transforma cada User em um UserDTO,
		// e o collect(toList()) junta os DTOs em uma nova lista.
		return ResponseEntity.ok().body(listDto);
	}
}
