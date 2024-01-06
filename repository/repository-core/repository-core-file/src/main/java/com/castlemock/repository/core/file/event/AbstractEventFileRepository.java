package com.castlemock.repository.core.file.event;

import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.event.model.EventFile;
import com.google.common.base.Function;

public abstract class AbstractEventFileRepository<T extends EventFile, D> extends FileRepository<T, D, String> {

    protected AbstractEventFileRepository(final Function<T, D> typeConverter, final Function<D, T> objectConverter) {
        super(typeConverter, objectConverter);
    }

}
