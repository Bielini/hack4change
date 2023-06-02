package com.capgemini.hack4change.domain.repository;

import com.capgemini.hack4change.domain.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {

}
