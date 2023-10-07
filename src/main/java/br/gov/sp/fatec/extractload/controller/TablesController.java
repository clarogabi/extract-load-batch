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
    public ResponseEntity<Void> deleteTable(String tableId) {
        appTableService.deleteAppTable(Long.valueOf(tableId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<DataTableResponse> getTable(String tableId) {
        return new ResponseEntity<>(appTableMapper.dtoToResponse(appTableService
                .getAppTableById(Long.valueOf(tableId))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreatedObjectResponse> postTable(DataTableRequest dataTableRequest) {
        return new ResponseEntity<>(new CreatedObjectResponse()
                .uid(String.valueOf(appTableService.createAppTable(appTableMapper.requestToDto(dataTableRequest)))),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> putTable(String tableId, DataTableRequest dataTableRequest) {
        appTableService.updateAppTable(appTableMapper.requestToDto(tableId, dataTableRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
