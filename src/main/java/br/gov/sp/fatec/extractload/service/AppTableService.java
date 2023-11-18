package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.AppTableDto;
import br.gov.sp.fatec.extractload.domain.mapper.AppTableMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadAppTable;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadAppTableRepository;
import br.gov.sp.fatec.extractload.repository.ExtractLoadBundledAppTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(value = "primaryTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class AppTableService {

    private final ExtractLoadAppTableRepository extractLoadAppTableRepository;

    private final ExtractLoadBundledAppTableRepository extractLoadBundledAppTableRepository;

    private final AppTableMapper appTableMapper;

    public ExtractLoadAppTable findAppTableById(final Long tableId) {
        Optional<ExtractLoadAppTable> appTable = extractLoadAppTableRepository.findById(tableId);
        return appTable.orElseThrow(() -> new NotFoundProblem("Registro não encontrado."));
    }

    public AppTableDto getAppTableById(final Long tableId) {
        return appTableMapper.entityToDto(findAppTableById(tableId));
    }

    public Long createAppTable(final AppTableDto appTableDto) {
        return extractLoadAppTableRepository.save(appTableMapper.dtoToEntity(appTableDto)).getUid();
    }

    public void deleteAppTable(final Long tableId) {
        if (extractLoadAppTableRepository.existsById(tableId)) {
            if (extractLoadBundledAppTableRepository.existsByTargetAppTableUid(tableId)
                || extractLoadBundledAppTableRepository.existsBySourceAppTableUid(tableId)) {
                throw new UnprocessableEntityProblem(String
                    .format("Registro [%s] está atribuído a um ou mais pacotes de extração e carregamento, não é possível excluir.", tableId));
            }
            extractLoadAppTableRepository.deleteById(tableId);
        }
    }

    public void updateAppTable(final AppTableDto appTableDto) {
        var entity = findAppTableById(appTableDto.uid());
        entity.setAppTablePhysicalName(appTableDto.appTablePhysicalName());
        entity.setUpdateDateTime(LocalDateTime.now());
        extractLoadAppTableRepository.save(entity);
    }

}
