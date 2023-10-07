package br.gov.sp.fatec.extractload.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi extractAndLoadApi() {
        return GroupedOpenApi
                .builder()
                .addOpenApiCustomiser(openApi -> openApi.setInfo(new Info()
                                .title("Swagger Extract And Load Data Batch")
                                .description("The Extract and Load Data Batch Swagger UI offers the ability to perform " +
                                        "API requests such as GET, POST, PUT and DELETE to different endpoints in the " +
                                        "backend service like data sources, data bundles, batch execution, etc.")
                                .version("1.0.0")
                                .contact(new Contact()
                                        .email("gabriela.claro@fatec.sp.gov.br"))
                                .license(new License()
                                        .name("Apache 2.0")
                                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"))))
                .group("extract-load-batch-api")
                .pathsToMatch("/v1/**")
                .build();
    }

    @Bean
    public OpenAPI extractAndLoadOpenAPI() {
        return new OpenAPI()
                .tags(List.of(new Tag().name("Datasource").description("Datasource Configuration Properties"),
                        new Tag().name("Tables").description("Tables Properties Definitions"),
                        new Tag().name("Bundles").description("Tables Bundles Definitions"),
                        new Tag().name("BundledTables").description("Bundled Tables Definitions"),
                        new Tag().name("Batch").description("Batch Job Execution")))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .description("Valid JWT Token")
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .servers(List.of(new Server().url("http://localhost:8080")));
    }
}
