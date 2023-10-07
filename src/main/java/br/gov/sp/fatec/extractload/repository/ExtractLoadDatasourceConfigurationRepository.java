package br.gov.sp.fatec.extractload.repository;

import br.gov.sp.fatec.extractload.entity.ExtractLoadDatasourceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractLoadDatasourceConfigurationRepository extends JpaRepository<ExtractLoadDatasourceConfiguration, Long> {
}