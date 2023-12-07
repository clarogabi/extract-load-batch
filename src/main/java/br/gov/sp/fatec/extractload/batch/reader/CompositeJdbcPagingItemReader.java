package br.gov.sp.fatec.extractload.batch.reader;

import lombok.NoArgsConstructor;
import org.springframework.batch.item.database.JdbcPagingItemReader;

@NoArgsConstructor
public class CompositeJdbcPagingItemReader<T> extends JdbcPagingItemReader<T> {

    private PageProcessor<T> pageProcessor;

    public void setPageProcessor(final PageProcessor<T> pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    @Override
    protected void doReadPage() {
        super.doReadPage();

        if (!results.isEmpty()) {
            pageProcessor.process(results);
        }
    }

}
