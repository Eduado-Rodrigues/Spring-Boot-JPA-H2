package com.edutec.usuario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutec.usuario.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
