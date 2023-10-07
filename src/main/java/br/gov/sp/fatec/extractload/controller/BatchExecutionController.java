package br.gov.sp.fatec.extractload.controller;

import br.gov.sp.fatec.extractload.api.BatchApi;
import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.api.model.JobExecutionResponse;
import br.gov.sp.fatec.extractload.api.model.JobParametersRequest;
import br.gov.sp.fatec.extractload.domain.mapper.JobExecutionMapper;
import br.gov.sp.fatec.extractload.domain.mapper.JobParameterMapper;
import br.gov.sp.fatec.extractload.service.BatchExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchExecutionController implements BatchApi {

    private final BatchExecutionService batchExecutionService;

    private final JobParameterMapper jobParameterMapper;

    private final JobExecutionMapper jobExecutionMapper;

    @Override
    public ResponseEntity<JobExecutionResponse> getBatchJobExecution(Long jobExecutionId) {
        return new ResponseEntity<>(jobExecutionMapper.map(batchExecutionService.findJobExecutionById(jobExecutionId)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postBatchJobExecution(JobParametersRequest request) {
        return new ResponseEntity<>(batchExecutionService.startJob(jobParameterMapper.map(request)), HttpStatus.CREATED);
    }

}
