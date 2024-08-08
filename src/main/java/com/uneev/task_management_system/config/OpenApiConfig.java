package com.uneev.task_management_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ruslan",
                        email = "spaceejs@gmail.com"
                ),
                description = "OpenAPI documentation for the Task Management System",
                title = "Task Management System Specification"
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8080/api/v1"
                )
        },
        security = {
                @SecurityRequirement(name = "Bearer Auth")
        }
)
@SecurityScheme(
        name = "Bearer Auth",
        description = "Enter your JWT token to authorize",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
