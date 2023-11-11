package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.StepMetadataResponse;
import br.gov.sp.fatec.extractload.utils.ExtractLoadUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.batch.core.StepExecution;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel="spring",
    imports = { ExtractLoadUtils.class },
    uses = { BatchStatusMapper.class, ExitStatusMapper.class })
public interface StepExecutionMapper {

    @Mapping(target = "batchStatus", source = "status")
    @Mapping(target = "stepStartTime", source = "startTime")
    @Mapping(target = "stepEndTime", source = "endTime")
    @Mapping(target = "exitMessage", source = "exitStatus.exitDescription")
    @Mapping(target = "stepElapsedTime", expression = "java(ExtractLoadUtils.findElapsedTime(stepExecution.getStartTime(), stepExecution.getEndTime()))")
    StepMetadataResponse map(StepExecution stepExecution);

    List<StepMetadataResponse> mapList(Collection<StepExecution> stepExecutions);

}
