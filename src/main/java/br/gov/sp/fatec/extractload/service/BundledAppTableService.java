package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.mapper.BundledAppTableMapper;
import br.gov.sp.fatec.extractload.repository.ExtractLoadBundledAppTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(value = "extractLoadDataSourceTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class BundledAppTableService {

    @Autowired
    private ExtractLoadBundledAppTableRepository bundledAppTableRepository;

    @Autowired
    private BundledAppTableMapper bundledAppTableMapper;

    public List<BundledAppTableDto> findBundledAppTablesByDataBundleId(Long dataBundleId) {
        return bundledAppTableMapper.mapList(bundledAppTableRepository
                .findAllByDataBundleUidOrderByRelationalOrderingNumberAsc(dataBundleId));
    }

}
