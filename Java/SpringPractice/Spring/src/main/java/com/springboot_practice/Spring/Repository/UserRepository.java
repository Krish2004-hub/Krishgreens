package com.springboot_practice.Spring.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot_practice.Spring.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
	Optional<UserEntity> findByUsername(String username);

}
