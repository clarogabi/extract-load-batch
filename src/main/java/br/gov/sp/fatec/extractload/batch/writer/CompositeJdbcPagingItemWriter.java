package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompositeJdbcPagingItemWriter extends ClassifierCompositeItemWriter<RowMappedDto> {

    public CompositeJdbcPagingItemWriter(Classifier<RowMappedDto, ItemWriter<? super RowMappedDto>> loadItemWriterClassifier) {
        setClassifier(loadItemWriterClassifier);
    }

}
