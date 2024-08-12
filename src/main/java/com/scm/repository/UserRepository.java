package com.scm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);

    // Here we can create two types of method
    // 1.custom finder
    // 2.query base method
}