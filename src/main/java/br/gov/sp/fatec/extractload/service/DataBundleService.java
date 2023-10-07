package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.domain.dto.DataBundleDto;
import br.gov.sp.fatec.extractload.domain.mapper.DataBundleMapper;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataBundle;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityException;
import br.gov.sp.fatec.extractload.repository.ExtractLoadDataBundleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(value = "extractLoadDataSourceTransactionManager", propagation = Propagation.REQUIRES_NEW)
public class DataBundleService {

    @Autowired
    private ExtractLoadDataBundleRepository dataBundleRepository;

    @Autowired
    private DataBundleMapper dataBundleMapper;

    public DataBundleDto findDataBundleById(Long dataBundleId) {
        Optional<ExtractLoadDataBundle> dataBundle = dataBundleRepository.findByUid(dataBundleId);
        return dataBundleMapper.mapToDto(dataBundle
                .orElseThrow(() -> new UnprocessableEntityException("Data bundle not found.")));
    }

    public String findDataBundleNameById(Long dataBundleId) {
        return findDataBundleById(dataBundleId).getDataBundleName();
    }

}
