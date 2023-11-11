package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import static java.util.Objects.nonNull;

public class LoadItemWriterClassifier implements Classifier<RowMappedDto, ItemWriter<? super RowMappedDto>> {

    private final ItemWriter<RowMappedDto> insertWriter;
    private final ItemWriter<RowMappedDto> updateWriter;

    public LoadItemWriterClassifier(ItemWriter<RowMappedDto> insertWriter, ItemWriter<RowMappedDto> updateWriter) {
        this.insertWriter = insertWriter;
        this.updateWriter = updateWriter;
    }

    @Override
    public ItemWriter<RowMappedDto> classify(RowMappedDto row) {

        if (nonNull(row) && LoadModeEnum.INSERT.equals(row.getLoadMode())) {
            return insertWriter;
        } else {
            return updateWriter;
        }
    }

}