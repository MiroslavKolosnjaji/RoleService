package com.example.roleservice.web.handler;

import com.example.roleservice.dto.RoleDTO;
import com.example.roleservice.model.Role;
import com.example.roleservice.service.RoleService;
import com.example.roleservice.web.router.RoleRouterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * @author Miroslav Kološnjaji
 */
@Component
@RequiredArgsConstructor
public class RoleHandler {

    public static final String ROLE_ID = "roleId";
    private final RoleService roleService;
    private final Validator validator;


    public Mono<ServerResponse> createRole(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RoleDTO.class)
                .doOnSuccess(this::validate)
                .flatMap(roleService::save)
                .flatMap(savedRole -> ServerResponse.created(UriComponentsBuilder.fromPath(RoleRouterConfig.ROLE_PATH_ID)
                        .buildAndExpand(savedRole.getId()).toUri())
                        .build());
    }

    public Mono<ServerResponse> updateRole(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RoleDTO.class)
                .doOnSuccess(this::validate)
                .flatMap(roleService::update)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(roleDTO -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getRoleById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(roleService.findById(Long.valueOf(serverRequest.pathVariable(ROLE_ID)))
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))), Role.class);
    }

    public Mono<ServerResponse> getAllRoles(ServerRequest serverRequest) {
        return ServerResponse.ok().body(roleService.findAll(), Role.class);
    }

    public Mono<ServerResponse> deleteRole(ServerRequest serverRequest) {
        return roleService.findById(Long.valueOf(serverRequest.pathVariable(ROLE_ID)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(roleDTO -> roleService.deleteById(roleDTO.getId()))
                .then(ServerResponse.noContent().build());
    }

    private void validate(RoleDTO roleDTO) {
        Errors errors = new BeanPropertyBindingResult(roleDTO, "roleDTO");
        validator.validate(roleDTO, errors);

        if (errors.hasErrors())
            throw new ServerWebInputException(errors.toString());

    }
}
