package com.castlemock.repository.core.file.project;

import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.project.model.ProjectFile;
import com.google.common.base.Function;

public abstract class AbstractProjectFileRepository<T extends ProjectFile, D> extends FileRepository<T, D, String> {

    public AbstractProjectFileRepository(final Function<T, D> typeConverter, final Function<D, T> objectConverter) {
        super(typeConverter, objectConverter);
    }
}
