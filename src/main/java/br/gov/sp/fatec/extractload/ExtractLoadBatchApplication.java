package br.gov.sp.fatec.extractload;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class, JacksonAutoConfiguration.class })
@OpenAPIDefinition(info=@Info(title="Name of project"))
public class ExtractLoadBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtractLoadBatchApplication.class, args);
    }

}
