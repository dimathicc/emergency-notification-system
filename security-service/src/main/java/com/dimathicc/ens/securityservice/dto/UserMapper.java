package com.dimathicc.ens.securityservice.dto;

import com.dimathicc.ens.securityservice.model.Role;
import com.dimathicc.ens.securityservice.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", imports = {Role.class})
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(Role.USER)")
    @Mapping(target = "password", expression = "java(encoder.encode(request.password()))")
    User mapToEntity(SignUpRequest request, @Context PasswordEncoder encoder);
}
