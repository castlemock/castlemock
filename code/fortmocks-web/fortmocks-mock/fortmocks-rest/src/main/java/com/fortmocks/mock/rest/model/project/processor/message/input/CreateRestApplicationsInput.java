package com.fortmocks.mock.rest.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestApplicationsInput implements Input {

    @NotNull
    private Long restProjectId;
    @NotNull
    private List<RestApplicationDto> restApplications;

    public CreateRestApplicationsInput(Long restProjectId, List<RestApplicationDto> restApplications) {
        this.restProjectId = restProjectId;
        this.restApplications = restApplications;
    }

    public Long getRestProjectId() {
        return restProjectId;
    }

    public void setRestProjectId(Long restProjectId) {
        this.restProjectId = restProjectId;
    }

    public List<RestApplicationDto> getRestApplications() {
        return restApplications;
    }

    public void setRestApplications(List<RestApplicationDto> restApplications) {
        this.restApplications = restApplications;
    }
}
