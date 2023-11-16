package br.gov.sp.fatec.extractload.controller;

import br.gov.sp.fatec.extractload.api.DataSourceApi;
import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.api.model.DataSourcePropertiesRequest;
import br.gov.sp.fatec.extractload.api.model.DataSourcePropertiesResponse;
import br.gov.sp.fatec.extractload.domain.mapper.DataSourceMapper;
import br.gov.sp.fatec.extractload.service.DataSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataSourceController implements DataSourceApi {

    private final DataSourceService dataSourceService;

    private final DataSourceMapper dataSourceMapper;

    @Override
    public ResponseEntity<Void> deleteDataSourceProperties(final Long dataSourceId) {
        dataSourceService.deleteDataSource(dataSourceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataSourcePropertiesResponse> getDataSourceProperties(final Long dataSourceId) {
        return new ResponseEntity<>(dataSourceMapper.dtoToResponse(dataSourceService.getDataSourceProperties(dataSourceId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postDataSourceConnection(final DataSourcePropertiesRequest dataSourcePropertiesRequest) {
        return new ResponseEntity<>(new CreatedObjectResponse()
            .uid(dataSourceService.createDataSource(dataSourceMapper.requestToDto(dataSourcePropertiesRequest))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> putDataSourceProperties(final Long datasourceId, final DataSourcePropertiesRequest dataSourcePropertiesRequest) {
        dataSourceService.updateDataSource(dataSourceMapper.requestToDto(datasourceId, dataSourcePropertiesRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
