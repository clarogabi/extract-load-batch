package br.gov.sp.fatec.extractload.repository;

import br.gov.sp.fatec.extractload.entity.ExtractLoadDataBundle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtractLoadDataBundleRepository extends JpaRepository<ExtractLoadDataBundle, Long> {

    Optional<ExtractLoadDataBundle> findByUid(Long uid);

    boolean existsBySourceDatasourceConfigUid(Long datasourceUid);

    boolean existsByTargetDatasourceConfigUid(Long datasourceUid);

}