package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.DataBundleDto;
import br.gov.sp.fatec.extractload.domain.mapper.DataBundleMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataBundle;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataSourceConfiguration;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadDataBundleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(value = "primaryTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class DataBundleService {

    private final ExtractLoadDataBundleRepository dataBundleRepository;

    private final DataBundleMapper dataBundleMapper;

    private final DataSourceService datasourceService;

    private ExtractLoadDataBundle findExtractLoadDataBundleById(final Long dataBundleId) {
        return dataBundleRepository.findByUid(dataBundleId)
            .orElseThrow(() -> new NotFoundProblem("Registro não encontrado."));
    }

    public DataBundleDto findDataBundleById(final Long dataBundleId) {
        return dataBundleMapper.mapToDto(findExtractLoadDataBundleById(dataBundleId));
    }

    public Long createDataBundle(final DataBundleDto dataBundleDto) {
        return dataBundleRepository.save(dataBundleMapper.dtoToEntity(dataBundleDto)).getUid();
    }

    public void updateDataBundle(final DataBundleDto dataBundleDto) {
        var entity = findExtractLoadDataBundleById(dataBundleDto.uid());
        entity.setDataBundleName(dataBundleDto.dataBundleName());
        entity.setUpdateDateTime(LocalDateTime.now());

        var sourceDataSource = new ExtractLoadDataSourceConfiguration();
        sourceDataSource.setUid(datasourceService.getDataSourceProperties(dataBundleDto.sourceDataSourceId()).uid());
        entity.setSourceDataSourceConfig(sourceDataSource);

        var targetDataSource = new ExtractLoadDataSourceConfiguration();
        targetDataSource.setUid(datasourceService.getDataSourceProperties(dataBundleDto.targetDataSourceId()).uid());
        entity.setTargetDataSourceConfig(targetDataSource);

        dataBundleRepository.save(entity);
    }

    public void deleteDataBundle(final Long dataBundleId) {
        if (existsDataBundleById(dataBundleId)) {
            dataBundleRepository.deleteById(dataBundleId);
        }
    }

    public boolean existsDataBundleById(final Long dataBundleId) {
        return dataBundleRepository.existsById(dataBundleId);
    }

}
