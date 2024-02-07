package com.dimathicc.ens.userservice.mapper;

import com.dimathicc.ens.userservice.dto.UserRequest;
import com.dimathicc.ens.userservice.dto.UserResponse;
import com.dimathicc.ens.userservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserRequest, UserResponse> {

}
