package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.DataBundleDto;
import br.gov.sp.fatec.extractload.domain.mapper.DataBundleMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataBundle;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDatasourceConfiguration;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadDataBundleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(value = "extractLoadDataSourceTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class DataBundleService {

    private final ExtractLoadDataBundleRepository dataBundleRepository;

    private final DataBundleMapper dataBundleMapper;

    private final DatasourceService datasourceService;

    private ExtractLoadDataBundle findExtractLoadDataBundleById(Long dataBundleId) {
        return dataBundleRepository.findByUid(dataBundleId)
            .orElseThrow(() -> new NotFoundProblem("Registro não encontrado."));
    }

    public DataBundleDto findDataBundleById(Long dataBundleId) {
        return dataBundleMapper.mapToDto(findExtractLoadDataBundleById(dataBundleId));
    }

    public String findDataBundleNameById(Long dataBundleId) {
        return findDataBundleById(dataBundleId).getDataBundleName();
    }

    public Long createDataBundle(DataBundleDto dataBundleDto) {
        return dataBundleRepository.save(dataBundleMapper.dtoToEntity(dataBundleDto)).getUid();
    }

    public void updateDataBundle(DataBundleDto dataBundleDto) {
        var entity = findExtractLoadDataBundleById(dataBundleDto.getUid());
        entity.setDataBundleName(dataBundleDto.getDataBundleName());
        entity.setUpdateDateTime(Timestamp.valueOf(LocalDateTime.now()));

        ExtractLoadDatasourceConfiguration sourceDatasource = new ExtractLoadDatasourceConfiguration();
        sourceDatasource.setUid(datasourceService.getDatasourceProperties(dataBundleDto.getSourceDatasourceId()).getUid());
        entity.setSourceDatasourceConfig(sourceDatasource);

        ExtractLoadDatasourceConfiguration targetDatasource = new ExtractLoadDatasourceConfiguration();
        targetDatasource.setUid(datasourceService.getDatasourceProperties(dataBundleDto.getTargetDatasourceId()).getUid());
        entity.setTargetDatasourceConfig(targetDatasource);

        dataBundleRepository.save(entity);
    }

    public void deleteDataBundle(Long dataBundleId) {
        if (existsDataBundleById(dataBundleId)) {
            dataBundleRepository.deleteById(dataBundleId);
        }
    }

    public boolean existsDataBundleById(Long dataBundleId) {
        return dataBundleRepository.existsById(dataBundleId);
    }

}
