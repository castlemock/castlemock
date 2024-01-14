/*
 * Copyright 2024 Karl Dahlgren
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

public final class SystemInformationTestBuilder {

    private SystemInformationTestBuilder(){

    }

    public static SystemInformation.Builder builder() {
        return SystemInformation.builder()
                .operatingSystemName("Linux")
                .showCastleMockHomeDirectory(true)
                .tomcatInfo("Tomcat")
                .availableProcessors(4)
                .freeMemory(88)
                .castleMockHomeDirectory("/home/castlemock/.castlemock")
                .javaVendor("OpenJDK")
                .javaVersion("14.0.2")
                .maxMemory(142)
                .tomcatBuilt("8.5.0")
                .tomcatVersion("8.5")
                .totalMemory(4173);
    }

}
