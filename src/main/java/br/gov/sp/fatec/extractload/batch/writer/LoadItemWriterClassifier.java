package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import java.util.Objects;

public class LoadItemWriterClassifier implements Classifier<RowMappedDto, ItemWriter<? super RowMappedDto>> {

    private ItemWriter<RowMappedDto> insertWriter;
    private ItemWriter<RowMappedDto> updateWriter;

    public LoadItemWriterClassifier(ItemWriter<RowMappedDto> insertWriter, ItemWriter<RowMappedDto> updateWriter) {
        this.insertWriter = insertWriter;
        this.updateWriter = updateWriter;
    }

    @Override
    public ItemWriter<RowMappedDto> classify(RowMappedDto row) {

        if (Objects.nonNull(row) && LoadModeEnum.INSERT.equals(row.getLoadMode())) {
            return insertWriter;
        } else {
            return updateWriter;
        }
    }

}