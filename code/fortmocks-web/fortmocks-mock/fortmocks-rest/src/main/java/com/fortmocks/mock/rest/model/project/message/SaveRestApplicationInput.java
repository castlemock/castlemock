package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SaveRestApplicationInput implements Input {

    private Long restProjectId;
    private RestApplicationDto restApplication;

    public Long getRestProjectId() {
        return restProjectId;
    }

    public void setRestProjectId(Long restProjectId) {
        this.restProjectId = restProjectId;
    }

    public RestApplicationDto getRestApplication() {
        return restApplication;
    }

    public void setRestApplication(RestApplicationDto restApplication) {
        this.restApplication = restApplication;
    }
}
