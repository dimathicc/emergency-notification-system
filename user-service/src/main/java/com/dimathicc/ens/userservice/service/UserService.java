package com.dimathicc.ens.userservice.service;

import com.dimathicc.ens.userservice.dto.UserRequest;
import com.dimathicc.ens.userservice.dto.UserResponse;
import com.dimathicc.ens.userservice.exception.UserNotFoundException;
import com.dimathicc.ens.userservice.exception.UserRegistrationException;
import com.dimathicc.ens.userservice.exception.UserUpdateException;
import com.dimathicc.ens.userservice.exception.UserValidationException;
import com.dimathicc.ens.userservice.mapper.UserMapper;
import com.dimathicc.ens.userservice.model.User;
import com.dimathicc.ens.userservice.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserResponse createUser(UserRequest request) {
        Optional<User> existingUser = repository.findByEmail(request.email());
        if (existingUser.isPresent()) {
            return update(existingUser.get().getId(), request);

        }
        if (repository.existsByEmailOrPhoneOrTelegramId(request.email(), request.phone(), request.telegramId())) {
            throw new UserValidationException("Пользователь с таким email/phone/telegram уже существует");
        }

        try {
            return Optional.of(request)
                    .map(mapper::mapToEntity)
                    .map(repository::save)
                    .map(mapper::mapToResponseDto)
                    .orElseThrow(() -> new UserRegistrationException("Can not register user"));
        } catch (DataIntegrityViolationException e) {
            throw new UserRegistrationException(e.getMessage());
        }

    }

    public UserResponse retrieveUserById(Long id) {
        return repository.findById(id)
                .map(mapper::mapToResponseDto)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        return repository.findById(id)
                .map(user -> mapper.update(request, user))
                .map(repository::saveAndFlush)
                .map(mapper::mapToResponseDto)
                .orElseThrow(() -> new UserUpdateException("can't update user information"));
    }

    public Boolean deleteById(Long id) {
        return repository.findById(id)
                .map(user -> { repository.delete(user); return user; })
                .isPresent();
    }

    public List<UserResponse> retrieveAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToResponseDto)
                .toList();
    }


    public UserResponse update(Long userId, UserRequest request) {
        try {
            return repository.findById(userId)
                    .map(user -> mapper.update(request, user))
                    .map(repository::saveAndFlush)
                    .map(mapper::mapToResponseDto)
                    .orElseThrow(() -> new UserNotFoundException(
                            "User with id " + userId + " was not found")
                    );
        } catch (DataIntegrityViolationException e) {
            throw new UserRegistrationException(e.getMessage());
        }
    }
}
