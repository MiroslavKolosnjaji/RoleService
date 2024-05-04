package com.example.roleservice.mapper;

import com.example.roleservice.dto.RoleDTO;
import com.example.roleservice.model.Role;
import org.mapstruct.Mapper;

/**
 * @author Miroslav Kološnjaji
 */
@Mapper
public interface RoleMapper {

    RoleDTO roleToRoleDTO(Role role);
    Role roleDTOToRole(RoleDTO roleDTO);

}
