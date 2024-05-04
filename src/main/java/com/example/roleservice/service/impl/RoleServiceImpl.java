package com.example.roleservice.service.impl;

import com.example.roleservice.dto.RoleDTO;
import com.example.roleservice.mapper.RoleMapper;
import com.example.roleservice.repository.RoleRepository;
import com.example.roleservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Miroslav Kološnjaji
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Mono<RoleDTO> save(RoleDTO roleDTO) {
        return roleRepository.save(roleMapper.roleDTOToRole(roleDTO))
                .map(roleMapper::roleToRoleDTO);
    }

    @Override
    public Mono<RoleDTO> update(RoleDTO roleDTO) {

        return roleRepository.findById(roleDTO.getId())
                .map(foundRole -> {
                    foundRole.setRoleName(roleDTO.getRoleName());
                    return foundRole;
                }).flatMap(roleRepository::save)
                .map(roleMapper::roleToRoleDTO);
    }

    @Override
    public Mono<RoleDTO> findById(Long id) {
        return roleRepository.findById(id).map(roleMapper::roleToRoleDTO);
    }

    @Override
    public Flux<RoleDTO> findAll() {
        return roleRepository.findAll().map(roleMapper::roleToRoleDTO);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return roleRepository.deleteById(id);
    }
}
