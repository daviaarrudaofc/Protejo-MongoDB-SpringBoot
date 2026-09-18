package com.daviarruda.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.daviarruda.workshopmongo.domain.Post;
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
	
	@GetMapping (value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id){
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDTO){
		User obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()	// pega a URL da requisição atual, ex: /users
	            .path("/{id}")										// adiciona /{id} no final da URL
	            .buildAndExpand(obj.getId())						// substitui {id} pelo ID do usuário criado
	            .toUri();											// transforma essa URL em um objeto URI

	    return ResponseEntity.created(uri).build();   //O usuário foi criado, aqui está a URI dele, mas não vou devolver nada no corpo
	}
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "{id}" )
	public ResponseEntity<Void> update(@RequestBody UserDTO objDTO, @PathVariable String id){
		User obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();

		
	}
	@GetMapping (value = "{id}/posts")
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getPosts());
	}
	
	
}
