package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.mapper.BundledAppTableMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadBundledAppTable;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.repository.ExtractLoadBundledAppTableRepository;
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
public class BundledAppTableService {

    private final ExtractLoadBundledAppTableRepository bundledAppTableRepository;

    private final BundledAppTableMapper bundledAppTableMapper;

    private final DataBundleService dataBundleService;

    private final AppTableService appTableService;

    private ExtractLoadBundledAppTable findExtractLoadBundledAppByUidAndBundleUid(final Long bundledTableId, final Long bundleId) {
        return bundledAppTableRepository.findByUidAndExtractLoadDataBundleUid(bundledTableId, bundleId)
            .orElseThrow(() -> new NotFoundProblem("Registro não encontrado."));
    }

    public BundledAppTableDto findBundledAppTableByBundledTableIdAndBundleId(final Long bundleId, final Long bundledTableId) {
        return bundledAppTableMapper.mapToDto(findExtractLoadBundledAppByUidAndBundleUid(bundledTableId, bundleId));
    }

    public void deleteBundledTable(final Long bundleId, final Long bundledTableId) {
        if (bundledAppTableRepository.existsByUidAndExtractLoadDataBundleUid(bundledTableId, bundleId)) {
            bundledAppTableRepository.deleteByUid(bundledTableId);
        }
    }

    public Long addBundledTable(final Long bundleId, final BundledAppTableDto bundledAppTableDto) {
        if (dataBundleService.existsDataBundleById(bundleId)) {
            return bundledAppTableRepository.save(bundledAppTableMapper.mapToEntityForAddition(bundledAppTableDto, bundleId)).getUid();
        } else {
            throw new NotFoundProblem("Registro não encontrado.");
        }
    }

    public void updateBundledTable(final Long bundleId, final Long bundledTableId, final BundledAppTableDto bundledAppTableDto) {
        var entity = findExtractLoadBundledAppByUidAndBundleUid(bundledTableId, bundleId);

        entity.setSourceAppTable(appTableService.findAppTableById(bundledAppTableDto.sourceAppTableId()));
        entity.setSourceAppTable(appTableService.findAppTableById(bundledAppTableDto.targetAppTableId()));
        entity.setRelationalOrderingNumber(bundledAppTableDto.relationalOrderingNumber());
        entity.setExtractCustomQuery(bundledAppTableDto.extractCustomQuery());
        entity.setLoadCustomInsertQuery(bundledAppTableDto.loadCustomInsertQuery());
        entity.setLoadCustomUpdateQuery(bundledAppTableDto.loadCustomUpdateQuery());
        entity.setUpdateDateTime(LocalDateTime.now());

        bundledAppTableRepository.save(entity);
    }

}
