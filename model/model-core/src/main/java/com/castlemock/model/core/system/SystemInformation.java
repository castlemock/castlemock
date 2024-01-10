/*
 * Copyright 2016 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.model.core.system;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * The {@link SystemInformation} contains information about the system
 * which the application is running on.
 * @author Karl Dahlgren
 * @since 1.7
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SystemInformation.Builder.class)
public class SystemInformation {

    @XmlElement
    private final String operatingSystemName;

    @XmlElement
    private final String javaVersion;

    @XmlElement
    private final String javaVendor;

    @XmlElement
    private final String tomcatBuilt;

    @XmlElement
    private final String tomcatInfo;

    @XmlElement
    private final String tomcatVersion;

    @XmlElement
    private final long totalMemory;

    @XmlElement
    private final long maxMemory;

    @XmlElement
    private final long freeMemory;

    @XmlElement
    private final int availableProcessors;

    @XmlElement
    private final String castleMockHomeDirectory;

    @XmlElement
    private final boolean showCastleMockHomeDirectory;

    public SystemInformation(final Builder builder){
        this.operatingSystemName = Objects.requireNonNull(builder.operatingSystemName, "operatingSystemName");
        this.javaVersion = Objects.requireNonNull(builder.javaVersion, "javaVersion");
        this.javaVendor = Objects.requireNonNull(builder.javaVendor, "javaVendor");
        this.tomcatBuilt = Objects.requireNonNull(builder.tomcatBuilt, "tomcatBuilt");
        this.tomcatInfo = Objects.requireNonNull(builder.tomcatInfo, "tomcatInfo");
        this.tomcatVersion = Objects.requireNonNull(builder.tomcatVersion, "tomcatVersion");
        this.totalMemory = Objects.requireNonNull(builder.totalMemory, "totalMemory");
        this.maxMemory = Objects.requireNonNull(builder.maxMemory, "maxMemory");
        this.freeMemory = Objects.requireNonNull(builder.freeMemory, "freeMemory");
        this.availableProcessors = Objects.requireNonNull(builder.availableProcessors, "availableProcessors");
        this.castleMockHomeDirectory = Objects.requireNonNull(builder.castleMockHomeDirectory, "castleMockHomeDirectory");
        this.showCastleMockHomeDirectory = Objects.requireNonNull(builder.showCastleMockHomeDirectory, "showCastleMockHomeDirectory");
    }

    public String getOperatingSystemName() {
        return operatingSystemName;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getJavaVendor() {
        return javaVendor;
    }

    public String getTomcatBuilt() {
        return tomcatBuilt;
    }

    public String getTomcatInfo() {
        return tomcatInfo;
    }

    public String getTomcatVersion() {
        return tomcatVersion;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public String getCastleMockHomeDirectory() {
        return castleMockHomeDirectory;
    }

    public boolean isShowCastleMockHomeDirectory() {
        return showCastleMockHomeDirectory;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String operatingSystemName;
        private String javaVersion;
        private String javaVendor;
        private String tomcatBuilt;
        private String tomcatInfo;
        private String tomcatVersion;
        private Long totalMemory;
        private Long maxMemory;
        private Long freeMemory;
        private Integer availableProcessors;
        private String castleMockHomeDirectory;
        private Boolean showCastleMockHomeDirectory;

        private Builder() {
        }


        public Builder operatingSystemName(final String operatingSystemName) {
            this.operatingSystemName = operatingSystemName;
            return this;
        }

        public Builder javaVersion(final String javaVersion) {
            this.javaVersion = javaVersion;
            return this;
        }

        public Builder javaVendor(final String javaVendor) {
            this.javaVendor = javaVendor;
            return this;
        }

        public Builder tomcatBuilt(final String tomcatBuilt) {
            this.tomcatBuilt = tomcatBuilt;
            return this;
        }

        public Builder tomcatInfo(final String tomcatInfo) {
            this.tomcatInfo = tomcatInfo;
            return this;
        }

        public Builder tomcatVersion(final String tomcatVersion) {
            this.tomcatVersion = tomcatVersion;
            return this;
        }

        public Builder totalMemory(final long totalMemory) {
            this.totalMemory = totalMemory;
            return this;
        }

        public Builder maxMemory(final long maxMemory) {
            this.maxMemory = maxMemory;
            return this;
        }

        public Builder freeMemory(final long freeMemory) {
            this.freeMemory = freeMemory;
            return this;
        }

        public Builder availableProcessors(final int availableProcessors) {
            this.availableProcessors = availableProcessors;
            return this;
        }

        public Builder castleMockHomeDirectory(final String castleMockHomeDirectory) {
            this.castleMockHomeDirectory = castleMockHomeDirectory;
            return this;
        }

        public Builder showCastleMockHomeDirectory(final boolean showCastleMockHomeDirectory) {
            this.showCastleMockHomeDirectory = showCastleMockHomeDirectory;
            return this;
        }

        public SystemInformation build() {
            return new SystemInformation(this);
        }
    }
}
