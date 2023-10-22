package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.JobExecutionResponse;
import br.gov.sp.fatec.extractload.utils.ExtractLoadUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.batch.core.JobExecution;

@Mapper(componentModel="spring",
    imports = { ExtractLoadUtils.class },
    uses = { BatchStatusMapper.class, ExitStatusMapper.class, ThrowableListJoiner.class, StepExecutionMapper.class })
public abstract class JobExecutionMapper {

    @Mapping(target = "jobExecutionId", source = "id")
    @Mapping(target = "jobName", source = "jobInstance.jobName")
    @Mapping(target = "batchStatus", source = "status")
    @Mapping(target = "jobStartTime", source = "startTime")
    @Mapping(target = "jobEndTime", source = "endTime")
    @Mapping(target = "exitMessage", source = "failureExceptions")
    @Mapping(target = "steps", source = "stepExecutions")
    @Mapping(target = "jobElapsedTime", expression = "java( ExtractLoadUtils.findElapsedTime(jobExecution.getStartTime(), jobExecution.getEndTime()) )")
    public abstract JobExecutionResponse map(JobExecution jobExecution);

}
