package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.AppTableDto;
import br.gov.sp.fatec.extractload.domain.mapper.AppTableMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadAppTable;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadAppTableRepository;
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
public class AppTableService {

    @Autowired
    private ExtractLoadAppTableRepository extractLoadAppTableRepository;

    @Autowired
    private AppTableMapper appTableMapper;

    private ExtractLoadAppTable findAppTableById(Long tableId) {
        Optional<ExtractLoadAppTable> appTable = extractLoadAppTableRepository.findById(tableId);
        return appTable.orElseThrow(() -> new NotFoundProblem("App table not found."));
    }

    public AppTableDto getAppTableById(Long tableId) {
        return appTableMapper.entityToDto(findAppTableById(tableId));
    }

    public Long createAppTable(AppTableDto appTableDto) {
        return extractLoadAppTableRepository.save(appTableMapper.dtoToEntity(appTableDto)).getUid();
    }

    public void deleteAppTable(Long tableId) {
        if (extractLoadAppTableRepository.existsById(tableId)) {
            extractLoadAppTableRepository.deleteById(tableId);
        }
    }

    public void updateAppTable(AppTableDto appTableDto) {
        ExtractLoadAppTable entity = findAppTableById(appTableDto.getUid());
        entity.setAppTablePhysicalName(appTableDto.getAppTablePhysicalName());
        entity.setUpdateDateTime(Timestamp.valueOf(LocalDateTime.now()));
    }

}
