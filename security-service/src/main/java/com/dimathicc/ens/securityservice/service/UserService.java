package com.dimathicc.ens.securityservice.service;

import com.dimathicc.ens.securityservice.dto.JwtAuthenticationResponse;
import com.dimathicc.ens.securityservice.dto.SignInRequest;
import com.dimathicc.ens.securityservice.dto.SignUpRequest;
import com.dimathicc.ens.securityservice.dto.UserMapper;
import com.dimathicc.ens.securityservice.exception.UserAlreadyExistsException;
import com.dimathicc.ens.securityservice.exception.UserBadCredentialsException;
import com.dimathicc.ens.securityservice.exception.UserNotFoundException;
import com.dimathicc.ens.securityservice.model.User;
import com.dimathicc.ens.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public Boolean register(SignUpRequest request) {
        return Optional.of(userRepository.findByEmail(request.getEmail()))
                .map(user -> {
                    if (user.isPresent()) {
                        throw new UserAlreadyExistsException("User with email: " + user.get().getEmail() + "already exists");
                    } else {
                        return request;
                    }
                })
                .map(req -> mapper.mapToEntity(req, passwordEncoder))
                .map(userRepository::saveAndFlush)
                .isPresent();
    }

    public JwtAuthenticationResponse authenticate(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (InternalAuthenticationServiceException e) {
            throw new UserNotFoundException("User with email: " + request.getEmail() + " not found");
        } catch (BadCredentialsException e) {
            throw new UserBadCredentialsException("Bad credentials");
        }

        User user = loadUserByUsername(request.getEmail());

        tokenService.deletePreviousUserToken(user);
        String jwt = jwtService.generateJwt(user);
        tokenService.createToken(user, jwt);

        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username + " not found"));
    }
}
