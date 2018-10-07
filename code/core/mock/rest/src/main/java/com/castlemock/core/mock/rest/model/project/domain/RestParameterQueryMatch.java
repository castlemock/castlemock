package com.castlemock.core.mock.rest.model.project.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestParameterQueryMatch {

    private RestParameterQuery query;
    private String match;

    @XmlElement
    public RestParameterQuery getQuery() {
        return query;
    }

    public void setQuery(RestParameterQuery query) {
        this.query = query;
    }

    @XmlElement
    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
}
