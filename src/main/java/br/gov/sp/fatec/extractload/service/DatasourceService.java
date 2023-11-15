package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.DatasourceDto;
import br.gov.sp.fatec.extractload.domain.mapper.DatasourceMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDatasourceConfiguration;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadDataBundleRepository;
import br.gov.sp.fatec.extractload.repository.ExtractLoadDatasourceConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(value = "extractLoadDataSourceTm", propagation = Propagation.REQUIRES_NEW)
public class DatasourceService {

    private final ExtractLoadDatasourceConfigurationRepository datasourceConfigurationRepository;
    private final ExtractLoadDataBundleRepository extractLoadDataBundleRepository;
    private final DatasourceMapper datasourceMapper;

    private ExtractLoadDatasourceConfiguration findDatasourceById(Long datasourceId) {
        Optional<ExtractLoadDatasourceConfiguration> datasource = datasourceConfigurationRepository.findById(datasourceId);
        return datasource.orElseThrow(() -> new NotFoundProblem("Registro não encontrado."));
    }

    public DatasourceDto getDatasourceProperties(Long datasourceId) {
        return datasourceMapper.entityToDto(findDatasourceById(datasourceId));
    }

    public Long createDatasource(DatasourceDto datasourceDto) {
        return datasourceConfigurationRepository.save(datasourceMapper.dtoToEntity(datasourceDto)).getUid();
    }

    public void deleteDatasource(Long datasourceId) {
        if (datasourceConfigurationRepository.existsById(datasourceId)) {
            if (existsDataBundleByDatasourceId(datasourceId)) {
                throw new UnprocessableEntityProblem(String
                    .format("Registro [%s] está atribuído a um ou mais pacotes de extração e carregamento, não foi possível excluir!", datasourceId));
            }
            datasourceConfigurationRepository.deleteById(datasourceId);
        }
    }

    private boolean existsDataBundleByDatasourceId(Long datasourceId) {
        return extractLoadDataBundleRepository.existsBySourceDatasourceConfigUid(datasourceId)
            || extractLoadDataBundleRepository.existsByTargetDatasourceConfigUid(datasourceId);
    }

    public void updateDatasource(DatasourceDto datasourceDto) {
        var entity = findDatasourceById(datasourceDto.getUid());
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
        datasourceConfigurationRepository.save(entity);
    }

}
