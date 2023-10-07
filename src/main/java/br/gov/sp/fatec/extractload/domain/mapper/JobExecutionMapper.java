package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.JobExecutionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.batch.core.JobExecution;

@Mapper(componentModel="spring", uses = { BatchStatusMapper.class })
public interface JobExecutionMapper {

    @Mapping(target = "jobExecutionId", source = "id")
    @Mapping(target = "jobName", source = "jobInstance.jobName")
    @Mapping(target = "batchStatus", source = "status")
    @Mapping(target = "jobStartTime", source = "startTime")
    @Mapping(target = "jobEndTime", source = "endTime")
//    @Mapping(target = "exitStatus", source = "exitStatus", qualifiedBy = ExitStatusMapper.class)
    @Mapping(target = "exitStatus", source = "exitStatus", ignore = true)
//    @Mapping(target = "exitMessage", source = "failureExceptions", qualifiedBy = ThrowableListJoiner.class)
    @Mapping(target = "exitMessage", source = "failureExceptions", ignore = true)
    @Mapping(target = "jobElapsedTime", ignore = true) //TODO
    JobExecutionResponse map(JobExecution jobExecution);

}
