package com.castlemock.web.basis.model;

import java.util.Objects;

public class UpdateProfileRequest {

    private String username;
    private String password;
    private String email;
    private String fullName;

    private UpdateProfileRequest() {

    }

    private UpdateProfileRequest(final Builder builder){
        this.username = Objects.requireNonNull(builder.username);
        this.password = Objects.requireNonNull(builder.password);
        this.email = builder.email;
        this.fullName = builder.fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String username;
        private String password;
        private String email;
        private String fullName;

        private Builder() {
        }

        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UpdateProfileRequest build() {
            return new UpdateProfileRequest(this);
        }
    }

}
