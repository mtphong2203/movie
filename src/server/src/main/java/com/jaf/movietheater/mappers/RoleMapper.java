package com.jaf.movietheater.mappers;

import org.mapstruct.*;

import com.jaf.movietheater.dtos.roles.RoleCreateUpdateDTO;
import com.jaf.movietheater.dtos.roles.RoleMasterDTO;
import com.jaf.movietheater.entities.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(RoleCreateUpdateDTO DTO);

    Role toEntity(RoleCreateUpdateDTO DTO, @MappingTarget Role role);

    RoleMasterDTO toMasterDTO(Role role);
}
