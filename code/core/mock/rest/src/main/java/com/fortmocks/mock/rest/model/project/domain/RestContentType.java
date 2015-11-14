package com.fortmocks.mock.rest.model.project.domain;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public enum RestContentType {

    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    TEXT_XML("text/xml"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    MULTIPART_MIXED("multipart/mixed");

    private String contentType;

    private RestContentType(final String contentType){
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
