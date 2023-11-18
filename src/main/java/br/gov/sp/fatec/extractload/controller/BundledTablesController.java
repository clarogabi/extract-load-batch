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
    public ResponseEntity<Void> deleteBundledTable(final Long bundleId, final Long bundledTableId) {
        bundledAppTableService.deleteBundledTable(bundleId, bundledTableId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<BundledTableResponse> getBundledTable(final Long bundleId, final Long bundledTableId) {
        return new ResponseEntity<>(bundledAppTableMapper.mapToResponse(bundledAppTableService
            .findBundledAppTableByBundledTableIdAndBundleId(bundleId, bundledTableId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postBundledTables(final Long bundleId, final BundledTableRequest bundledTableRequest) {
        return new ResponseEntity<>(new CreatedObjectResponse().uid(bundledAppTableService
            .addBundledTable(bundleId, bundledAppTableMapper.requestToDto(bundledTableRequest))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> putBundledTable(final Long bundleId, final Long bundledTableId, final BundledTableRequest bundledTableRequest) {
        bundledAppTableService.updateBundledTable(bundleId, bundledTableId, bundledAppTableMapper.requestToDto(bundledTableRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
