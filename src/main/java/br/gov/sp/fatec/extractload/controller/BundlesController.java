package br.gov.sp.fatec.extractload.controller;

import br.gov.sp.fatec.extractload.api.BundlesApi;
import br.gov.sp.fatec.extractload.api.model.BundleRequest;
import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.api.model.DataBundleRequest;
import br.gov.sp.fatec.extractload.api.model.DataBundleResponse;
import br.gov.sp.fatec.extractload.domain.mapper.DataBundleMapper;
import br.gov.sp.fatec.extractload.service.DataBundleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BundlesController implements BundlesApi {

    private final DataBundleService dataBundleService;

    private final DataBundleMapper dataBundleMapper;

    @Override
    public ResponseEntity<Void> deleteBundle(final Long bundleId) {
        dataBundleService.deleteDataBundle(bundleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataBundleResponse> getBundle(final Long bundleId) {
        return new ResponseEntity<>(dataBundleMapper.mapToResponse(dataBundleService.findDataBundleById(bundleId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postBundle(final DataBundleRequest dataBundleRequest) {
        return new ResponseEntity<>(new CreatedObjectResponse()
            .uid(dataBundleService.createDataBundle(dataBundleMapper.requestToDto(dataBundleRequest))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> putBundle(final Long bundleId, final BundleRequest bundleRequest) {
        dataBundleService.updateDataBundle(dataBundleMapper.requestToDto(bundleId, bundleRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
