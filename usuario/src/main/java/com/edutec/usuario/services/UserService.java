package com.edutec.usuario.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutec.usuario.entities.User;
import com.edutec.usuario.repositories.UserRepository;


@Service
public class UserService {
    
    @Autowired
	private UserRepository userRepository;
	
	public User salvar(User user) {
		return userRepository.save(user);
	}
	
	public List<User> listaCliente() {
		return userRepository.findAll();
	}
	
	public Optional<User> buscarPorId(Long id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> buscarPorNome(String nome) {
        return userRepository.findByNome(nome);
    }
	
	public void removerPorId(Long id) {
		userRepository.deleteById(id);
	}
}
