package com.dimathicc.ens.securityservice.repository;

import com.dimathicc.ens.securityservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
}
