package com.edutec.usuario.resource;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.edutec.usuario.entities.User;
import com.edutec.usuario.services.UserService;
import com.edutec.usuario.services.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	 	@Autowired
	    private UserService userService;
	 	
	 	@Autowired
	    private UserServiceImpl userServiceImpl;
	 	
	 	@Autowired
		private ModelMapper modelMapper;
	    
	    @GetMapping
	    @ResponseStatus(HttpStatus.OK)
	    public List<User> findAll() {
	        return userService.findAll();
	    }
	    
	    @GetMapping("/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    public User findById(@PathVariable("id") Long id) {
	        return userService.findById(id)
	                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
	    }
	    
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public User insert(@RequestBody User user) {
	        return userService.insert(user);
	    }
	    
	    @DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void delete(@PathVariable("id") Long id) {
			userService.findById(id)
				.map(cliente -> {
					userService.delete(cliente.getId());
					return Void.TYPE;
				}) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
		}
	    
	    @PutMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void update(@PathVariable("id") Long id, @RequestBody User user) {
			userService.findById(id)
				.map(userBase -> {
					modelMapper.map(user, userBase);
					userServiceImpl.salvar(userBase);
					return Void.TYPE;
				}) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
		}
}
