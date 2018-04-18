<%@ include file="../../../../includes.jspf"%>

<%--
  ~ Copyright 2018 Karl Dahlgren
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<c:url var="graphql_resource_update_url"  value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/object/${graphQLObjectType.id}/update" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}"><spring:message code="graphql.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}"><spring:message code="graphql.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="graphql.updategraphqlobject.header.object" arguments="${graphQLObjectType.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="graphql.updategraphqlobject.header.object" arguments="${graphQLObjectType.name}"/></h1>
            </div>
            <div class="menu" align="right">

            </div>
        </div>
        <form:form action="${graphql_resource_update_url}" method="POST" commandName="graphQLObjectType">
            <div class="content-summary">
                <table class="formTable">
                    <tr>
                        <td class="column1"><label path="name"><spring:message code="graphql.updategraphqlobject.label.name"/></label></td>
                        <td class="column2"><label path="name">${graphQLObjectType.name}</label></td>
                    </tr>
                    <tr>
                        <td class="column1"><label path="description"><spring:message code="graphql.updategraphqlobject.label.description"/></label></td>
                        <td class="column2"><label path="description">${graphQLObjectType.description}</label></td>
                    </tr>
                </table>
            </div>

            <h2 class="decorated"><span><spring:message code="graphql.updategraphqlobject.header.attributes"/></span></h2>
            <c:choose>
                <c:when test="${graphQLObjectType.attributes.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover sortable">
                            <col width="10%">
                            <col width="10%">
                            <col width="80%">

                            <tr>
                                <th><spring:message code="graphql.updategraphqlobject.column.name"/></th>
                                <th><spring:message code="graphql.updategraphqlobject.column.type"/></th>
                                <th><spring:message code="graphql.updategraphqlobject.column.value"/></th>
                            </tr>

                            <c:forEach items="${graphQLObjectType.attributes}" var="graphQLAttribute" varStatus="loopStatus">
                                <tr>
                                    <td>${graphQLAttribute.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${graphQLAttribute.attributeType == 'OBJECT_TYPE'}">
                                                <a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/object/${graphQLAttribute.typeId}"/>">${graphQLAttribute.typeName}</a>
                                            </c:when>
                                            <c:when test="${graphQLAttribute.attributeType == 'ENUM'}">
                                                <a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/enum/${graphQLAttribute.typeId}"/>">${graphQLAttribute.typeName}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <spring:message code="graphql.type.attributetype.${graphQLAttribute.attributeType}"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${graphQLAttribute.listable == true}">[ ]</c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${graphQLAttribute.attributeType == 'OBJECT_TYPE'}">
                                            </c:when>
                                            <c:when test="${graphQLAttribute.attributeType == 'ENUM'}">
                                            </c:when>
                                            <c:otherwise>
                                                <form:input path="attributes[${loopStatus.index}].id" id="attributes[${loopStatus.index}].id" value="${graphQLAttribute.id}" type="hidden" />
                                                <form:input class="form-control" path="attributes[${loopStatus.index}].value" id="attributes[${loopStatus.index}].value" value="${graphQLAttribute.value}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                        <button class="btn btn-success" type="submit" name="submit"><i class="fa fa-check-circle"></i><spring:message code="graphql.updategraphqlobject.button.updateapplication"/></button>
                        <a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/object/${graphQLObjectType.id}"/>" class="btn btn-danger"><i class="fa fa-times"></i> <spring:message code="graphql.updategraphqlobject.button.cancel"/></a>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <spring:message code="graphql.updategraphqlobject.label.noattributes"/>
                </c:otherwise>
            </c:choose>
        </form:form>
    </section>
</div>