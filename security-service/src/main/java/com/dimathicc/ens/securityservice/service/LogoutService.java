package com.dimathicc.ens.securityservice.service;

import com.dimathicc.ens.securityservice.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = jwtService.extractJwt(request);

        if (jwt != null && !jwt.isEmpty()) {
            String email = jwtService.extractEmail(jwt);
            User user = userService.loadUserByUsername(email);

            tokenService.deletePreviousUserToken(user);
            SecurityContextHolder.clearContext();
        }
    }
}
