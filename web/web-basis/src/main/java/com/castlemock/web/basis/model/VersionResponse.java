package com.castlemock.web.basis.model;

import java.util.Objects;

public class VersionResponse {

    private String version;

    private VersionResponse(){

    }

    private VersionResponse(final Builder builder){
        this.version = Objects.requireNonNull(builder.version);
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionResponse that = (VersionResponse) o;
        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public String toString() {
        return "VersionResponse{" +
                "version='" + version + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private String version;

        private Builder() {
        }


        public Builder version(final String version) {
            this.version = version;
            return this;
        }

        public VersionResponse build() {
            return new VersionResponse(this);
        }
    }
}
