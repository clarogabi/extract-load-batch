package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.Serial;

import static br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum.INSERT;
import static java.util.Objects.nonNull;

@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoadItemWriterClassifier implements Classifier<RowMappedDto, ItemWriter<? super RowMappedDto>> {

    @Serial
    private static final long serialVersionUID = 1;

    private final transient ItemWriter<RowMappedDto> insertJdbcItemWriter;
    private final transient ItemWriter<RowMappedDto> updateJdbcItemWriter;

    public LoadItemWriterClassifier(final ItemWriter<RowMappedDto> insertJdbcItemWriter,
        final ItemWriter<RowMappedDto> updateJdbcItemWriter) {
        this.insertJdbcItemWriter = insertJdbcItemWriter;
        this.updateJdbcItemWriter = updateJdbcItemWriter;
    }

    @Override
    public ItemWriter<RowMappedDto> classify(final RowMappedDto row) {
        if (nonNull(row) && INSERT == row.getLoadMode()) {
            return insertJdbcItemWriter;
        } else {
            return updateJdbcItemWriter;
        }
    }

}