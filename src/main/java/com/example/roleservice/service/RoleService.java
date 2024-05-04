package com.example.roleservice.service;

import com.example.roleservice.dto.RoleDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Miroslav Kološnjaji
 */
public interface RoleService {

    Mono<RoleDTO> save(RoleDTO roleDTO);
    Mono<RoleDTO> update(RoleDTO roleDTO);
    Mono<RoleDTO> findById(Long id);
    Flux<RoleDTO> findAll();
    Mono<Void> deleteById(Long id);

}
