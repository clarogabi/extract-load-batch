package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.BatchStatusEnum;
import org.mapstruct.Mapper;
import org.springframework.batch.core.BatchStatus;

@Mapper(componentModel = "spring")
public interface BatchStatusMapper {

    BatchStatusEnum map(BatchStatus batchStatus);

}
