package com.dimathicc.ens.fileservice.service;

import com.dimathicc.ens.fileservice.dto.UserRequest;
import com.dimathicc.ens.fileservice.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users")
    ResponseEntity<List<UserResponse>> retrieveAllUsers();

    @PostMapping("/api/users")
    ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request);
}
