package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.model.validation.NotNull;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteRestApplicationsInput implements Input{

    @NotNull
    private Long restProjectId;
    @NotNull
    private List<RestApplicationDto> restApplications;

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
