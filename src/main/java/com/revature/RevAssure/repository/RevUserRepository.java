package com.revature.RevAssure.repository;

import com.revature.RevAssure.model.RevUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevUserRepository extends JpaRepository<RevUser, Integer> {
    Optional<RevUser> findByUsername(String s);
}
