package com.castlemock.core.mock.graphql.model.project.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum GraphQLAttributeType {

    OBJECT_TYPE, STRING, ID, INT, FLOAT, BOOLEAN

}
