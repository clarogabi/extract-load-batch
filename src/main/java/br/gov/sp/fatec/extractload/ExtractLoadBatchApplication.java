package br.gov.sp.fatec.extractload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ExtractLoadBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtractLoadBatchApplication.class, args);
    }

}
