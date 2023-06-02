package com.capgemini.hack4change.domain.repository;

import java.util.Optional;

import com.capgemini.hack4change.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
