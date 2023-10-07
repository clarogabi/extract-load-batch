package br.gov.sp.fatec.extractload.repository;

import br.gov.sp.fatec.extractload.entity.ExtractLoadBundledAppTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtractLoadBundledAppTableRepository extends JpaRepository<ExtractLoadBundledAppTable, Long> {

    List<ExtractLoadBundledAppTable> findAllByDataBundleUidOrderByRelationalOrderingNumberAsc(Long dataBundleUid);

    Optional<ExtractLoadBundledAppTable> findFirstByDataBundleUidAndUid(Long dataBundleUid, Long uid);

    boolean existsByDataBundleUidAndUid(Long dataBundleUid, Long uid);

    void deleteByDataBundleUidAndUid(Long dataBundleUid, Long uid);
}