package com.dimathicc.ens.userservice.service;

import com.dimathicc.ens.userservice.dto.UserRequest;
import com.dimathicc.ens.userservice.dto.UserResponse;
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

    public UserResponse retrieveUserById(Long id) {
        return repository.findById(id)
                .map(mapper::mapToResponseDto)
                .orElseThrow();
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        return repository.findById(id)
                .map(user -> mapper.update(request, user))
                .map(repository::saveAndFlush)
                .map(mapper::mapToResponseDto)
                .orElseThrow();
    }

    public Boolean deleteById(Long id) {
        return repository.findById(id)
                .map(user -> { repository.delete(user); return user; })
                .isPresent();
    }
}
