package com.example.pix.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.Generated;

import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Pix Example API",
        version = "1.0",
        description = "API de exemplo para transações Pix (com TDD, Docker, CI/CD e K8s).",
        license = @License(name = "MIT")
    )
)
@Configuration
@Generated
public class OpenApiConfig {}

