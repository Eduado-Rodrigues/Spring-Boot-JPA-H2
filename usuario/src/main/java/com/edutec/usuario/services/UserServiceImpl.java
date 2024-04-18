package com.edutec.usuario.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutec.usuario.entities.User;
import com.edutec.usuario.repositories.UserRepository;
import com.edutec.usuario.services.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl {
	@Autowired
	private UserRepository userRepository;
	
	public User salvar(User user) {
		return userRepository.save(user);
	}

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> findAll() {
        return userRepository.findAll();
    }
	
	public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    public User insert(User user) {
        return userRepository.save(user);
    }
    
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }
    
    public User update(Long id, User user) {
        Optional<User> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        User entity = optionalEntity.get();
        updateData(entity, user);
        return userRepository.save(entity);
    }
    
    private void updateData(User entity, User user) {
        entity.setNome(user.getNome());
        entity.setEmail(user.getEmail());
        entity.setTelefone(user.getTelefone());
        entity.setPassword(user.getPassword());
    }
}
