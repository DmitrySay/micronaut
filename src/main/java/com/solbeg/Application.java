package com.solbeg;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "MYSQL (r2dbc) Micronaut example",
                version = "1.0",
                description = "Application API",
                license = @License(name = "MIT"),
                contact = @Contact(url = "https://example.com", name = "support", email = "support@example.com")
        )
)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
