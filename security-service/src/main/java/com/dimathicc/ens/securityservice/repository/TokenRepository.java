package com.dimathicc.ens.securityservice.repository;

import com.dimathicc.ens.securityservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUserId(Long id);
    Optional<Token> findByJwt(String jwt);
}
