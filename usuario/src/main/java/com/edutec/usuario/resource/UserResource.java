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

@RestController
@RequestMapping("/users")
public class UserResource {
	
	 	@Autowired
	    private UserService userService;
	 	
	 	@Autowired
		private ModelMapper modelMapper;
	    
	    @GetMapping("/cliente/{id}")
		public Long getClienteId(@PathVariable Long id) {
			User user = userService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
			return user.getId();
		}
		
		@GetMapping("/cliente/{nome}")
		public User getClienteNome(@PathVariable String nome) {
			User user = userService.buscarPorNome(nome)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
			return user;
		}

		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public User salvar(@RequestBody User user) {
			if (user.getNome() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do cliente é obrigatório.");
			}
			return userService.salvar(user);
		}
		
		@GetMapping
		@ResponseStatus(HttpStatus.OK)
		public List<User> listaCliente() {
			return userService.listaCliente();
		}
		
		@GetMapping("/{id}")
		@ResponseStatus(HttpStatus.OK)
		public User buscarClientePorId(@PathVariable("id") Long id) {
			return userService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
		}
		
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void removerCliente(@PathVariable("id") Long id) {
			userService.buscarPorId(id)
				.map(user -> {
					userService.removerPorId(user.getId());
					return Void.TYPE;
				}) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
		}
		
		@PutMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void atualizarCliente(@PathVariable("id") Long id, @RequestBody User user) {
			userService.buscarPorId(id)
				.map(userBase -> {
					modelMapper.map(user, userBase);
					userService.salvar(userBase);
					return Void.TYPE;
				}) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
		}
}
