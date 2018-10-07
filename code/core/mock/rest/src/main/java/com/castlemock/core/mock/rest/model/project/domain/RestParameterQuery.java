package com.castlemock.core.mock.rest.model.project.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestParameterQuery {

    private String parameter;
    private String query;
    private boolean matchCase;
    private boolean matchAny;
    private boolean matchRegex;

    @XmlElement
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @XmlElement
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @XmlElement
    public boolean getMatchCase() {
        return matchCase;
    }

    public void setMatchCase(boolean matchCase) {
        this.matchCase = matchCase;
    }

    @XmlElement
    public boolean getMatchAny() {
        return matchAny;
    }

    public void setMatchAny(boolean matchAny) {
        this.matchAny = matchAny;
    }

    @XmlElement
    public boolean getMatchRegex() {
        return matchRegex;
    }

    public void setMatchRegex(boolean matchRegex) {
        this.matchRegex = matchRegex;
    }
}
