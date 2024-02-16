package com.dimathicc.ens.securityservice.service;

import com.dimathicc.ens.securityservice.exception.InvalidTokenException;
import com.dimathicc.ens.securityservice.exception.UserJwtNotFoundException;
import com.dimathicc.ens.securityservice.exception.UserNotFoundException;
import com.dimathicc.ens.securityservice.model.Token;
import com.dimathicc.ens.securityservice.model.User;
import com.dimathicc.ens.securityservice.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    public void deletePreviousUserToken(User user) {
        tokenRepository.findByUserId(user.getId()).ifPresent(tokenRepository::delete);
    }

    public void createToken(User user, String jwt) {
        tokenRepository.save(
                Token.builder()
                        .user(user)
                        .jwt(jwt)
                        .expired(false)
                        .revoked(false)
                        .build()
        );
    }

    public boolean isTokenValid(String token) {
        User user = extractUserDetailsFromToken(token);
        boolean isValid = tokenRepository.findByJwt(token)
                .map(jwt -> !jwt.isExpired() && !jwt.isRevoked())
                .orElse(false);

        if (isValid && jwtService.isTokenValid(token, user)) {
            return true;
        } else {
            throw new InvalidTokenException("Token is not valid: " + token);
        }
    }

    public User extractUserDetailsFromToken(String token) {
        String email = jwtService.extractEmail(token);
        try {
            return userService.loadUserByUsername(email);
        } catch (UserNotFoundException e) {
            throw new UserJwtNotFoundException(e.getMessage());
        }
    }

}
