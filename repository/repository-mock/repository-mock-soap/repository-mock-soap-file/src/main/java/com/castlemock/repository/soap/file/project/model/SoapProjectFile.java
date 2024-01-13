package com.castlemock.repository.soap.file.project.model;

import com.castlemock.repository.core.file.project.model.ProjectFile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "soapProject")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapProjectFile extends ProjectFile {

    private SoapProjectFile() {

    }

    private SoapProjectFile(final Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends ProjectFile.Builder<Builder> {

        public SoapProjectFile build() {
            return new SoapProjectFile(this);
        }

    }

}