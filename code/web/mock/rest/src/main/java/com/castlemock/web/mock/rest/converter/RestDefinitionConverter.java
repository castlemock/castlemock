package com.castlemock.web.mock.rest.converter;


import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;

import java.io.File;
import java.util.List;

public interface RestDefinitionConverter {

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplicationDto}.
     * @param file The file which will be converted to one or more {@link RestApplicationDto}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplicationDto} based on the provided file.
     */
    List<RestApplicationDto> convert(File file, boolean generateResponse);

}
