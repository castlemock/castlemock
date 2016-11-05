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

package com.castlemock.core.basis.model.system.service.dto;

/**
 * The {@link SystemInformationDto} contains information about the system
 * which the application is running on.
 * @author Karl Dahlgren
 * @since 1.7
 */
public class SystemInformationDto {

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

    public String getOperatingSystemName() {
        return operatingSystemName;
    }

    public void setOperatingSystemName(String operatingSystemName) {
        this.operatingSystemName = operatingSystemName;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getJavaVendor() {
        return javaVendor;
    }

    public void setJavaVendor(String javaVendor) {
        this.javaVendor = javaVendor;
    }

    public String getTomcatBuilt() {
        return tomcatBuilt;
    }

    public void setTomcatBuilt(String tomcatBuilt) {
        this.tomcatBuilt = tomcatBuilt;
    }

    public String getTomcatInfo() {
        return tomcatInfo;
    }

    public void setTomcatInfo(String tomcatInfo) {
        this.tomcatInfo = tomcatInfo;
    }

    public String getTomcatVersion() {
        return tomcatVersion;
    }

    public void setTomcatVersion(String tomcatVersion) {
        this.tomcatVersion = tomcatVersion;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }
}
