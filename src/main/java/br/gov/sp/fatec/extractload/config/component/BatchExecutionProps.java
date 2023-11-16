package br.gov.sp.fatec.extractload.config.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "batch.execution")
public record BatchExecutionProps(int concurrencyLimit, int chunkSize, int fetchSize, int retryLimit) {

}
