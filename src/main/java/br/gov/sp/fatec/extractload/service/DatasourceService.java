package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.DatasourceDto;
import br.gov.sp.fatec.extractload.domain.mapper.DatasourceMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDatasourceConfiguration;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadDatasourceConfigurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional(value = "extractLoadDataSourceTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class DatasourceService {

    @Autowired
    private ExtractLoadDatasourceConfigurationRepository datasourceConfigurationRepository;

    @Autowired
    private DatasourceMapper datasourceMapper;

    private ExtractLoadDatasourceConfiguration findDatasourceById(Long datasourceId) {
        Optional<ExtractLoadDatasourceConfiguration> datasource = datasourceConfigurationRepository.findById(datasourceId);
        return datasource.orElseThrow(() -> new NotFoundProblem("Datasource not found."));
    }

    public DatasourceDto getDatasourceProperties(Long datasourceId) {
        return datasourceMapper.entityToDto(findDatasourceById(datasourceId));
    }

    public Long createDatasource(DatasourceDto datasourceDto) {
        return datasourceConfigurationRepository.save(datasourceMapper.dtoToEntity(datasourceDto)).getUid();
    }

    public void deleteDatasource(Long datasourceId) {
        if (datasourceConfigurationRepository.existsById(datasourceId)) {
            datasourceConfigurationRepository.deleteById(datasourceId);
        }
    }

    public void updateDatasource(DatasourceDto datasourceDto) {
        ExtractLoadDatasourceConfiguration entity = findDatasourceById(datasourceDto.getUid());
        entity.setDatabaseName(datasourceDto.getDatabaseName());
        entity.setDatabaseProvider(datasourceDto.getDatabaseProvider());
        entity.setDatabasePlatform(datasourceDto.getDatabasePlatform());
        entity.setDatabaseConnectionUrl(datasourceDto.getDatabaseConnectionUrl());
        entity.setDatabaseUserName(datasourceDto.getDatabaseUserName());
        entity.setDatabasePassword(datasourceDto.getDatabasePassword());
        entity.setDriverClassName(datasourceDto.getDriverClassName());
        entity.setDatabaseDialect(datasourceDto.getDatabaseDialect());
        entity.setDatabaseSchema(datasourceDto.getDatabaseSchema());
        entity.setUpdateDateTime(Timestamp.valueOf(LocalDateTime.now()));
    }

}
