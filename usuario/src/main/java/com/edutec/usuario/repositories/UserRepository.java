package com.edutec.usuario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutec.usuario.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNome(String nome);
}
