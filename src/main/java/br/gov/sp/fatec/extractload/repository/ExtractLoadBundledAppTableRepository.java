package br.gov.sp.fatec.extractload.repository;

import br.gov.sp.fatec.extractload.entity.ExtractLoadBundledAppTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtractLoadBundledAppTableRepository extends JpaRepository<ExtractLoadBundledAppTable, Long> {

    List<ExtractLoadBundledAppTable> findAllByExtractLoadDataBundleUidOrderByRelationalOrderingNumberAsc(Long dataBundleUid);

    Optional<ExtractLoadBundledAppTable> findByUidAndExtractLoadDataBundleUid(Long uid, Long dataBundleUid);

    boolean existsByUidAndExtractLoadDataBundleUid(Long uid, Long dataBundleUid);

    void deleteByUid(Long uid);

    boolean existsBySourceAppTableUid(Long tableUid);

    boolean existsByTargetAppTableUid(Long tableUid);

}