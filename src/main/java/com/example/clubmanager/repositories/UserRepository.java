package com.example.clubmanager.repositories;

import com.example.clubmanager.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);
}
