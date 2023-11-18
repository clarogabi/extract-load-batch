package br.gov.sp.fatec.extractload.repository;

import br.gov.sp.fatec.extractload.entity.ExtractLoadBundledAppTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtractLoadBundledAppTableRepository extends JpaRepository<ExtractLoadBundledAppTable, Long> {

    Optional<ExtractLoadBundledAppTable> findByUidAndExtractLoadDataBundleUid(final Long uid, final Long dataBundleUid);

    boolean existsByUidAndExtractLoadDataBundleUid(final Long uid, final Long dataBundleUid);

    void deleteByUid(final Long uid);

    boolean existsBySourceAppTableUid(final Long tableUid);

    boolean existsByTargetAppTableUid(final Long tableUid);

}