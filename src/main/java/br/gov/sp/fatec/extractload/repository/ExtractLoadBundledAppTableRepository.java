package br.gov.sp.fatec.extractload.repository;

import br.gov.sp.fatec.extractload.entity.ExtractLoadBundledAppTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtractLoadBundledAppTableRepository extends JpaRepository<ExtractLoadBundledAppTable, Long> {

    List<ExtractLoadBundledAppTable> findAllByDataBundleUidOrderByRelationalOrderingNumberAsc(Long dataBundleUid);
}