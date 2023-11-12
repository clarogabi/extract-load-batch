package br.gov.sp.fatec.extractload.controller;

import br.gov.sp.fatec.extractload.api.DatasourceApi;
import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.api.model.DatasourcePropertiesRequest;
import br.gov.sp.fatec.extractload.api.model.DatasourcePropertiesResponse;
import br.gov.sp.fatec.extractload.domain.mapper.DatasourceMapper;
import br.gov.sp.fatec.extractload.service.DatasourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DatasourceController implements DatasourceApi {

    private final DatasourceService datasourceService;

    private final DatasourceMapper datasourceMapper;

    @Override
    public ResponseEntity<Void> deleteDatasourceProperties(Long datasourceId) {
        datasourceService.deleteDatasource(datasourceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DatasourcePropertiesResponse> getDatasourceProperties(Long datasourceId) {
        return new ResponseEntity<>(datasourceMapper.dtoToResponse(datasourceService.getDatasourceProperties(datasourceId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postDatasourceConnection(DatasourcePropertiesRequest datasourcePropertiesRequest) {
        return new ResponseEntity<>(new CreatedObjectResponse()
            .uid(datasourceService.createDatasource(datasourceMapper.requestToDto(datasourcePropertiesRequest))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> putDatasourceProperties(Long datasourceId, DatasourcePropertiesRequest datasourcePropertiesRequest) {
        datasourceService.updateDatasource(datasourceMapper.requestToDto(datasourceId, datasourcePropertiesRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
