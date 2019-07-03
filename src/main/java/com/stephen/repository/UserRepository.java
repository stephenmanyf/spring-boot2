package com.stephen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stephen.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByFirstName(String firstname);
}
