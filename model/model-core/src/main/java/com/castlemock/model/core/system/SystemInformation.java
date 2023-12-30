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

/**
 * The {@link SystemInformation} contains information about the system
 * which the application is running on.
 * @author Karl Dahlgren
 * @since 1.7
 */
public class SystemInformation {

    private final String operatingSystemName;
    private final String javaVersion;
    private final String javaVendor;
    private final String tomcatBuilt;
    private final String tomcatInfo;
    private final String tomcatVersion;
    private final long totalMemory;
    private final long maxMemory;
    private final long freeMemory;
    private final int availableProcessors;
    private final String castleMockHomeDirectory;
    private final boolean showCastleMockHomeDirectory;
    private final boolean showMongoProperties;

    public SystemInformation(final Builder builder){
        this.operatingSystemName = builder.operatingSystemName;
        this.javaVersion = builder.javaVersion;
        this.javaVendor = builder.javaVendor;
        this.tomcatBuilt = builder.tomcatBuilt;
        this.tomcatInfo = builder.tomcatInfo;
        this.tomcatVersion = builder.tomcatVersion;
        this.totalMemory = builder.totalMemory;
        this.maxMemory = builder.maxMemory;
        this.freeMemory = builder.freeMemory;
        this.availableProcessors = builder.availableProcessors;
        this.castleMockHomeDirectory = builder.castleMockHomeDirectory;
        this.showCastleMockHomeDirectory = builder.showCastleMockHomeDirectory;
        this.showMongoProperties = builder.showMongoProperties;
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

    public boolean isShowMongoProperties() {
        return showMongoProperties;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String operatingSystemName;
        private String javaVersion;
        private String javaVendor;
        private String tomcatBuilt;
        private String tomcatInfo;
        private String tomcatVersion;
        private long totalMemory;
        private long maxMemory;
        private long freeMemory;
        private int availableProcessors;
        private String castleMockHomeDirectory;
        private boolean showCastleMockHomeDirectory;
        private boolean showMongoProperties;

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

        public Builder showMongoProperties(final boolean showMongoProperties) {
            this.showMongoProperties = showMongoProperties;
            return this;
        }

        public SystemInformation build() {
            return new SystemInformation(this);
        }
    }
}
