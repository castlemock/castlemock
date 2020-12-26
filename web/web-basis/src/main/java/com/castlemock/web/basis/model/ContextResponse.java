package com.castlemock.web.basis.model;

import java.util.Objects;

public class ContextResponse {

    private String context;

    private ContextResponse(){

    }

    private ContextResponse(final Builder builder){
        this.context = Objects.requireNonNull(builder.context);
    }

    public String getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContextResponse that = (ContextResponse) o;
        return Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context);
    }

    @Override
    public String toString() {
        return "ContextResponse{" +
                "context='" + context + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private String context;

        private Builder() {
        }


        public Builder context(final String context) {
            this.context = context;
            return this;
        }

        public ContextResponse build() {
            return new ContextResponse(this);
        }
    }
}
