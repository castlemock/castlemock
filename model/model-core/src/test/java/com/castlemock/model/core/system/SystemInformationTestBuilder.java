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
