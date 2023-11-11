package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum.INSERT;
import static java.util.Objects.nonNull;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoadItemWriterClassifier implements Classifier<RowMappedDto, ItemWriter<? super RowMappedDto>> {

    private final ItemWriter<RowMappedDto> insertJdbcItemWriter;
    private final ItemWriter<RowMappedDto> updateJdbcItemWriter;

    public LoadItemWriterClassifier(ItemWriter<RowMappedDto> insertJdbcItemWriter,
                                    ItemWriter<RowMappedDto> updateJdbcItemWriter) {
        this.insertJdbcItemWriter = insertJdbcItemWriter;
        this.updateJdbcItemWriter = updateJdbcItemWriter;
    }

    @Override
    public ItemWriter<RowMappedDto> classify(RowMappedDto row) {
        if (nonNull(row) && INSERT == row.getLoadMode()) {
            return insertJdbcItemWriter;
        } else {
            return updateJdbcItemWriter;
        }
    }

}