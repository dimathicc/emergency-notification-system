package com.dimathicc.ens.userservice.service;

import com.dimathicc.ens.userservice.dto.UserRequest;
import com.dimathicc.ens.userservice.mapper.UserMapper;
import com.dimathicc.ens.userservice.model.User;
import com.dimathicc.ens.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Long createUser(UserRequest request) {
        return Optional.of(request)
                .map(mapper::mapToEntity)
                .map(repository::save)
                .map(User::getId)
                .orElseThrow();
    }
}
