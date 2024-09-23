package com.goose.pbtest.config.mapper;

import com.goose.pbtest.dto.EmitentJSONDTO;
import com.goose.pbtest.dto.EmitentResponseDTO;
import com.goose.pbtest.model.Emitent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class EmitentMapper {

    @Mapping(target = "bank", source = "name")
    @Mapping(target = "alphacode", source = "code")
    public abstract EmitentResponseDTO modelToResponseDTO(Emitent emitent);

    @Mapping(target = "id", ignore = true)
    public abstract Emitent jsonDTOToModel(EmitentJSONDTO jsondto);
}
