package br.gov.sp.fatec.extractload.config.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "open.api.doc")
public record OpenApiProps(String host, String title, String version, String description) {

}
