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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(value = "extractLoadDataSourceTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class BundledAppTableService {

    private final ExtractLoadBundledAppTableRepository bundledAppTableRepository;

    private final BundledAppTableMapper bundledAppTableMapper;

    private final DataBundleService dataBundleService;

    private final AppTableService appTableService;

    public List<BundledAppTableDto> findBundledAppTablesByDataBundleId(Long dataBundleId) {
        return bundledAppTableMapper.mapToDtoList(bundledAppTableRepository
                .findAllByExtractLoadDataBundleUidOrderByRelationalOrderingNumberAsc(dataBundleId));
    }

    private ExtractLoadBundledAppTable findExtractLoadBundledAppByUidAndBundleUid(Long bundledTableId, Long bundleId) {
        return bundledAppTableRepository.findByUidAndExtractLoadDataBundleUid(bundledTableId, bundleId)
                .orElseThrow(() -> new NotFoundProblem("Registro não encontrado."));
    }

    public BundledAppTableDto findBundledAppTableByBundledTableIdAndBundleId(Long bundleId, Long bundledTableId) {
        return bundledAppTableMapper.mapToDto(findExtractLoadBundledAppByUidAndBundleUid(bundledTableId, bundleId));
    }

    public void deleteBundledTable(Long bundleId, Long bundledTableId) {
        if (bundledAppTableRepository.existsByUidAndExtractLoadDataBundleUid(bundledTableId, bundleId)) {
            bundledAppTableRepository.deleteByUid(bundledTableId);
        }
    }

    public Long addBundledTable(Long bundleId, BundledAppTableDto bundledAppTableDto) {
        if (dataBundleService.existsDataBundleById(bundleId)) {
            return bundledAppTableRepository.save(bundledAppTableMapper.mapToEntityForAddition(bundledAppTableDto, bundleId))
                    .getUid();
        } else {
            throw new NotFoundProblem("Registro não encontrado.");
        }
    }

    public void updateBundledTable(Long bundleId, Long bundledTableId, BundledAppTableDto bundledAppTableDto) {
        var entity = findExtractLoadBundledAppByUidAndBundleUid(bundledTableId, bundleId);

        entity.setSourceAppTable(appTableService.findAppTableById(bundledAppTableDto.getSourceAppTableId()));
        entity.setSourceAppTable(appTableService.findAppTableById(bundledAppTableDto.getTargetAppTableId()));
        entity.setRelationalOrderingNumber(bundledAppTableDto.getRelationalOrderingNumber());
        entity.setExtractCustomQuery(bundledAppTableDto.getExtractCustomQuery());
        entity.setLoadCustomInsertQuery(bundledAppTableDto.getLoadCustomInsertQuery());
        entity.setLoadCustomUpdateQuery(bundledAppTableDto.getLoadCustomUpdateQuery());
        entity.setUpdateDateTime(Timestamp.valueOf(LocalDateTime.now()));

        bundledAppTableRepository.save(entity);
    }

}
