package com.daviarruda.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.daviarruda.workshopmongo.domain.Post;
import com.daviarruda.workshopmongo.domain.User;
import com.daviarruda.workshopmongo.dto.AuthorDTO;
import com.daviarruda.workshopmongo.dto.CommentDTO;
import com.daviarruda.workshopmongo.repository.PostRepository;
import com.daviarruda.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		userRepository.saveAll(Arrays.asList(maria,alex,bob));// SALVAR os usarios primeiro,para eles terem um id proprio no database
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu Viagem", "Vou viajar para São Paulo. Abraços! ", new AuthorDTO( maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje! ", new AuthorDTO( maria));
		
		
		CommentDTO c1 = new CommentDTO("Boa viagem Mano", sdf.parse("21/03/218"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/03/218"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia", sdf.parse("23/03/218"), new AuthorDTO(alex));
		
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		// adiciona em Maria as referências para post1 e post2,
		// que já foram salvos na coleção "post"
		userRepository.save(maria);
		// salva novamente Maria para atualizar o documento "user"
		// com as referências desses posts
		
	}

}
