package com.solbeg;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "MYSQL (r2dbc) Micronaut example",
                version = "1.0",
                description = "Application API",
                license = @License(name = "MIT"),
                contact = @Contact(url = "https://example.com", name = "support", email = "support@example.com")
        )
)
@SecurityRequirement(name = "Demo authorization")
@SecurityScheme(name = "Demo authorization", type = SecuritySchemeType.HTTP, scheme = "Bearer", bearerFormat = "JWT")
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
