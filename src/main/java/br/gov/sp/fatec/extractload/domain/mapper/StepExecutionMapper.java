package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.StepMetadataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.batch.core.StepExecution;

@Mapper(componentModel="spring", uses = { BatchStatusMapper.class })
public interface StepExecutionMapper {

    @Mapping(target = "batchStatus", source = "status")
    @Mapping(target = "stepStartTime", source = "startTime")
    @Mapping(target = "stepEndTime", source = "endTime")
//    @Mapping(target = "exitStatus", source = "exitStatus", qualifiedBy = ExitStatusMapper.class)
    @Mapping(target = "exitStatus", source = "exitStatus", ignore = true)
//    @Mapping(target = "exitMessage", source = "failureExceptions", qualifiedBy = ThrowableListJoiner.class)
    @Mapping(target = "exitMessage", source = "failureExceptions", ignore = true)
    @Mapping(target = "stepElapsedTime", ignore = true) //TODO
    StepMetadataResponse map(StepExecution stepExecution);

    //@Mapper
    //public interface PersonMapper {
    //    @Mapping(target = "fullName", source = ".", qualifiedByName="toFullName")
    //    PersonEntity toEntity(PersonVo person);
    //
    //    @Named("toFullName")
    //    String translateToFullName(PersonVo pserson) {
    //        return pserson.getFirstName() + pserson.getLastName();
    //    }
    //}

}
