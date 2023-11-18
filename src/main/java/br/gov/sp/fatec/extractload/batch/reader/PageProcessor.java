package br.gov.sp.fatec.extractload.batch.reader;

import java.util.List;

public interface PageProcessor<T> {

    void process(final List<T> page);

}
