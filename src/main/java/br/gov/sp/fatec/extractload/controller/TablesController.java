package br.gov.sp.fatec.extractload.controller;

import br.gov.sp.fatec.extractload.api.TablesApi;
import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.api.model.DataTableRequest;
import br.gov.sp.fatec.extractload.api.model.DataTableResponse;
import br.gov.sp.fatec.extractload.domain.mapper.AppTableMapper;
import br.gov.sp.fatec.extractload.service.AppTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TablesController implements TablesApi {

    private final AppTableService appTableService;

    private final AppTableMapper appTableMapper;

    @Override
    public ResponseEntity<Void> deleteTable(final Long tableId) {
        appTableService.deleteAppTable(tableId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataTableResponse> getTable(final Long tableId) {
        return new ResponseEntity<>(appTableMapper.dtoToResponse(appTableService.getAppTableById(tableId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postTable(final DataTableRequest dataTableRequest) {
        return new ResponseEntity<>(new CreatedObjectResponse()
            .uid(appTableService.createAppTable(appTableMapper.requestToDto(dataTableRequest))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> putTable(final Long tableId, final DataTableRequest dataTableRequest) {
        appTableService.updateAppTable(appTableMapper.requestToDto(tableId, dataTableRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
