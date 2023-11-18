package br.gov.sp.fatec.extractload.repository;

import br.gov.sp.fatec.extractload.entity.ExtractLoadDataSourceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractLoadDataSourceConfigurationRepository extends JpaRepository<ExtractLoadDataSourceConfiguration, Long> {
}