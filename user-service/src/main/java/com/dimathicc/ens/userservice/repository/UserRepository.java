package com.dimathicc.ens.userservice.repository;

import com.dimathicc.ens.userservice.model.User;
import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailOrPhoneOrTelegramId(String email, String phone, String telegramId);
    Optional<User> findByEmail(String email);
}
