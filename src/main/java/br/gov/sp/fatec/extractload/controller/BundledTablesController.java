package br.gov.sp.fatec.extractload.controller;

import br.gov.sp.fatec.extractload.api.BundledTablesApi;
import br.gov.sp.fatec.extractload.api.model.BundledTableRequest;
import br.gov.sp.fatec.extractload.api.model.BundledTableResponse;
import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.domain.mapper.BundledAppTableMapper;
import br.gov.sp.fatec.extractload.service.BundledAppTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BundledTablesController implements BundledTablesApi {

    private final BundledAppTableService bundledAppTableService;

    private final BundledAppTableMapper bundledAppTableMapper;

    @Override
    public ResponseEntity<Void> deleteBundledTable(String bundleId, String bundledTableId) {
        bundledAppTableService.deleteBundledTable(Long.valueOf(bundleId), Long.valueOf(bundledTableId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<BundledTableResponse> getBundledTable(String bundleId, String bundledTableId) {
        return new ResponseEntity<>(bundledAppTableMapper.mapToResponse(bundledAppTableService
                .findBundledAppTableByBundleIdAndBundledTableId(Long.valueOf(bundleId), Long.valueOf(bundledTableId))),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postBundledTables(String bundleId, BundledTableRequest bundledTableRequest) {
        return new ResponseEntity<>(new CreatedObjectResponse().uid(String.valueOf(bundledAppTableService
                .addBundledTable(Long.valueOf(bundleId), bundledAppTableMapper.requestToDto(bundledTableRequest)))),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> putBundledTable(String bundleId, String bundledTableId, BundledTableRequest bundledTableRequest) {
        bundledAppTableService.updateBundledTable(Long.valueOf(bundleId), Long.valueOf(bundledTableId),
                bundledAppTableMapper.requestToDto(bundledTableRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
