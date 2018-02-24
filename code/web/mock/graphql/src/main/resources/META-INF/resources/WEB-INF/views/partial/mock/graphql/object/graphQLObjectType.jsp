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

<c:url var="graphql_resource_update_url"  value="/web/graphql/project/${graphQLObjectType.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}"><spring:message code="graphql.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="graphql.graphqlobject.header.query" arguments="${graphQLObjectType.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="graphql.graphqlobject.header.query" arguments="${graphQLObjectType.name}"/></h1>
            </div>
            <div class="menu" align="right">

            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="graphql.graphqlobject.label.name"/></label></td>
                    <td class="column2"><label path="name">${graphQLObjectType.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="description"><spring:message code="graphql.graphqlobject.label.description"/></label></td>
                    <td class="column2"><label path="description">${graphQLObjectType.description}</label></td>
                </tr>
            </table>
        </div>

        <h2 class="decorated"><span><spring:message code="graphql.graphqlobject.header.attributes"/></span></h2>
        <c:choose>
            <c:when test="${graphQLObjectType.attributes.size() > 0}">
                <div class="table-responsive">
                    <table class="table table-striped table-hover sortable">
                        <col width="10%">
                        <col width="10%">
                        <col width="10%">
                        <col width="30%">
                        <col width="40%">

                        <tr>
                            <th><spring:message code="graphql.graphqlobject.column.name"/></th>
                            <th><spring:message code="graphql.graphqlobject.column.type"/></th>
                            <th><spring:message code="graphql.graphqlobject.column.nullable"/></th>
                            <th><spring:message code="graphql.graphqlobject.column.description"/></th>
                        </tr>

                        <c:forEach items="${graphQLObjectType.attributes}" var="graphQLAttribute" varStatus="loopStatus">
                            <tr>
                                <c:choose>
                                    <c:when test="${graphQLAttribute.attributeType == 'OBJECT_TYPE'}">
                                        <td><a href="<c:url value="/web/graphql/project/${graphQLProjectId}/object/${graphQLAttribute.typeId}"/>">${graphQLAttribute.name}</a></td>
                                    </c:when>
                                    <c:when test="${graphQLAttribute.attributeType == 'ENUM'}">
                                        <td><a href="<c:url value="/web/graphql/project/${graphQLProjectId}/enum/${graphQLAttribute.typeId}"/>">${graphQLAttribute.name}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${graphQLAttribute.name}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <spring:message code="graphql.type.attributetype.${graphQLAttribute.attributeType}"/>
                                    <c:choose>
                                        <c:when test="${graphQLAttribute.listable == true}">[ ]</c:when>
                                    </c:choose>
                                </td>
                                <td>${graphQLAttribute.nullable}</td>
                                <td>${graphQLAttribute.description}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <spring:message code="graphql.graphqlobject.label.noattributes"/>
            </c:otherwise>
        </c:choose>
    </section>
</div>