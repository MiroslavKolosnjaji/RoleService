package com.example.roleservice.web.handler;

import com.example.roleservice.dto.RoleDTO;
import com.example.roleservice.mapper.RoleMapper;
import com.example.roleservice.model.Role;
import com.example.roleservice.model.RoleName;
import com.example.roleservice.service.RoleService;
import com.example.roleservice.web.router.RoleRouterConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author Miroslav Kološnjaji
 */
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleEndPointTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMapper roleMapper;

    @Test
    void testCreateRole() {

        webTestClient.post()
                .uri(RoleRouterConfig.ROLE_PATH)
                .body(Mono.just(getTestRole(null)), Role.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("/api/role/3");
    }

    @Test
    void testCreateRoleBadRequest() {

        var role = getTestRole(null);
        role.setRoleName(null);

        webTestClient.post()
                .uri(RoleRouterConfig.ROLE_PATH)
                .body(Mono.just(role), Role.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void testUpdateRole() {

        var role = getTestRole(1L);
        role.setRoleName(RoleName.MODERATOR);

        webTestClient.put()
                .uri(RoleRouterConfig.ROLE_PATH_ID, role.getId())
                .body(Mono.just(role), Role.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateRoleBadRequest() {

        var role = getTestRole(1L);
        role.setRoleName(null);

        webTestClient.put()
                .uri(RoleRouterConfig.ROLE_PATH_ID, role.getId())
                .body(Mono.just(role), Role.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateRoleNotFound() {

        var role = getTestRole(99L);

        webTestClient.put()
                .uri(RoleRouterConfig.ROLE_PATH_ID, role.getId())
                .body(Mono.just(role), Role.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1)
    void testGetRoleById() {

        webTestClient.get()
                .uri(RoleRouterConfig.ROLE_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(RoleDTO.class);
    }

    @Test
    void testGetRoleByIdNotFound() {

        webTestClient.get()
                .uri(RoleRouterConfig.ROLE_PATH_ID, 99)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(2)
    void testGetAllRoles() {

        webTestClient.get()
                .uri(RoleRouterConfig.ROLE_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("$.size()", hasSize(greaterThan(1)));
    }

    @Test
    void testDeleteRole() {

        var role = getTestRole(1L);

        webTestClient.delete()
                .uri(RoleRouterConfig.ROLE_PATH_ID, role.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteRoleNotFound() {

        webTestClient.delete()
                .uri(RoleRouterConfig.ROLE_PATH_ID, 99)
                .exchange()
                .expectStatus().isNotFound();
    }

    private Role getTestRole(Long id) {
        return Role.builder().id(id).roleName(RoleName.ADMIN).build();
    }
}