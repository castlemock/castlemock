package com.castlemock.core.mock.graphql.model.project.domain;

import com.castlemock.core.basis.model.Saveable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@XmlRootElement
public class GraphQLObjectType extends GraphQLType {

    private List<GraphQLAttribute> attributes = new CopyOnWriteArrayList<GraphQLAttribute>();

    @XmlElement
    public List<GraphQLAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<GraphQLAttribute> attributes) {
        this.attributes = attributes;
    }
}
