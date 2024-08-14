package com.scm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Here we can create two types of method
    // 1.custom finder : we just can find any row by the column name just use findBy<columnNameInCapitalize>().
    Optional<User> findByEmail(String email);
    // 2.query base method
}