package br.gov.sp.fatec.extractload.config;

import br.gov.sp.fatec.extractload.config.component.OpenApiProps;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    public static final String AUTHORIZATION = "Authorization";
    private final OpenApiProps openApiProps;

    @Bean
    public OpenAPI openAPI() {
        var info = new Info()
            .title(openApiProps.title())
            .description(openApiProps.description())
            .version(openApiProps.version())
            .contact(new Contact().email("gabriela.claro@fatec.sp.gov.br"))
            .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"));
        return new OpenAPI()
            .info(info)
            .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION))
            .components(new Components()
                .addSecuritySchemes(AUTHORIZATION, new SecurityScheme()
                    .description("Valid JWT Token")
                    .name(AUTHORIZATION)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
            .servers(List.of(new Server().url(openApiProps.host())));
    }

}
