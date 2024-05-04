package com.example.roleservice.web.router;

import com.example.roleservice.web.handler.RoleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Miroslav Kološnjaji
 */
@Configuration
@RequiredArgsConstructor
public class RoleRouterConfig {

    public static final String ROLE_PATH = "/api/role";
    public static final String ROLE_PATH_ID = ROLE_PATH + "/{roleId}";

    private final RoleHandler roleHandler;

    @Bean
    public RouterFunction<ServerResponse> roleRoutes() {
        return route()
                .POST(ROLE_PATH, roleHandler::createRole)
                .PUT(ROLE_PATH_ID, roleHandler::updateRole)
                .GET(ROLE_PATH_ID, roleHandler::getRoleById)
                .GET(ROLE_PATH, roleHandler::getAllRoles)
                .DELETE(ROLE_PATH_ID, roleHandler::deleteRole)
                .build();

    }
}
