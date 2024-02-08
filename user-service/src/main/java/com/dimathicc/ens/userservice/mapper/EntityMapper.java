package com.dimathicc.ens.userservice.mapper;

import org.mapstruct.MappingTarget;

public interface EntityMapper<Entity, RequestDto, ResponseDto> {
    Entity mapToEntity(RequestDto requestDto);
    ResponseDto mapToResponseDto(Entity entity);
    Entity update(RequestDto requestDto, @MappingTarget Entity entity);
}
