package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager;
import br.gov.sp.fatec.extractload.domain.dto.DataSourceDto;
import br.gov.sp.fatec.extractload.domain.mapper.DataSourceMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataSourceConfiguration;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadDataSourceConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager.buildDataSourceProperties;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(value = "primaryTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class DataSourceService {

    private final ExtractLoadDataSourceConfigurationRepository dataSourceConfigurationRepository;
    private final DataSourceMapper dataSourceMapper;
    private final DataSourceRoutingManager dataSourceRoutingManager;

    private ExtractLoadDataSourceConfiguration findDataSourceById(final Long datasourceId) {
        Optional<ExtractLoadDataSourceConfiguration> datasource = dataSourceConfigurationRepository.findById(datasourceId);
        return datasource.orElseThrow(() -> new NotFoundProblem("Registro não encontrado."));
    }

    public DataSourceDto getDataSourceProperties(final Long datasourceId) {
        return dataSourceMapper.entityToDto(findDataSourceById(datasourceId));
    }

    public Long createDataSource(final DataSourceDto datasourceDto) {
        final var instanceId = dataSourceConfigurationRepository.save(dataSourceMapper.dtoToEntity(datasourceDto)).getUid();

        try {
            var dataSourceProperties = buildDataSourceProperties(datasourceDto);
            dataSourceRoutingManager.addConnection(instanceId, dataSourceProperties);
        } catch (SQLException e) {
            log.error("Error when attempting to add new data source connection.", e);
            throw new UnprocessableEntityProblem(format("%s%s","Ocorreu um erro ao adicionar nova conexão com o banco de dados. ",
                "Verifique os dados informados e tente novamente mais tarde."), e.getMessage());
        }

        log.info("Data source connection properties saved and new connection added with ID [{}].", instanceId);
        return instanceId;
    }

    public void deleteDataSource(final Long datasourceId) {
        if (dataSourceConfigurationRepository.existsById(datasourceId)) {
            dataSourceConfigurationRepository.deleteById(datasourceId);
        }

        dataSourceRoutingManager.removeConnection(datasourceId);
        log.info("Data source properties and connection ID [{}] successfully removed.", datasourceId);
    }

    public void updateDataSource(final DataSourceDto datasourceDto) {
        var entity = findDataSourceById(datasourceDto.uid());

        entity.setDatabaseName(datasourceDto.databaseName());
        entity.setDatabaseHost(datasourceDto.hostName());
        entity.setDatabaseNumberPort(datasourceDto.numberPort());
        entity.setDatabaseUserName(datasourceDto.userName());
        entity.setDatabasePassword(datasourceDto.password());
        entity.setDatabaseProductName(datasourceDto.databaseDriver());
        entity.setUpdateDateTime(LocalDateTime.now());
        dataSourceConfigurationRepository.save(entity);

        try {
            dataSourceRoutingManager.replaceConnection(datasourceDto.uid(), buildDataSourceProperties(datasourceDto));
        } catch (SQLException e) {
            log.error("Error when attempting to replace data source connection.", e);
            throw new UnprocessableEntityProblem(format("%s%s","Ocorreu um erro ao atualizar a conexão com o banco de dados. ",
                "Verifique os dados informados e tente novamente mais tarde."), e.getMessage());
        }

        log.info("Data source properties and connection updated with ID [{}].", datasourceDto.uid());
    }

    public List<DataSourceDto> findAllDataSources() {
        return dataSourceMapper.fromEntityList(dataSourceConfigurationRepository.findAll());
    }

}
