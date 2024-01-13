package com.castlemock.repository.rest.file.project.model;

import com.castlemock.repository.core.file.project.model.ProjectFile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "restProject")
@XmlAccessorType(XmlAccessType.NONE)
public class RestProjectFile extends ProjectFile {

    private RestProjectFile() {

    }

    private RestProjectFile(final Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends ProjectFile.Builder<Builder> {

        public RestProjectFile build() {
            return new RestProjectFile(this);
        }

    }

}