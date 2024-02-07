package com.dimathicc.ens.userservice.mapper;

public interface EntityMapper<Entity, RequestDto, ResponseDto> {
    Entity mapToEntity(RequestDto requestDto);
    ResponseDto mapToResponseDto(Entity entity);
}
