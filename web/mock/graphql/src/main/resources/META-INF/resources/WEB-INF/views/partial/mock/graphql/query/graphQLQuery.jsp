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

<c:url var="graphql_resource_update_url"  value="/web/graphql/project/${graphQLQuery.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}"><spring:message code="graphql.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}"><spring:message code="graphql.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="graphql.graphqlquery.header.query" arguments="${graphQLQuery.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="graphql.graphqlquery.header.query" arguments="${graphQLQuery.name}"/></h1>
            </div>
            <div class="menu" align="right">

            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="graphql.graphqlquery.label.name"/></label></td>
                    <td class="column2"><label path="name">${graphQLQuery.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="description"><spring:message code="graphql.graphqlquery.label.description"/></label></td>
                    <td class="column2"><label path="description">${graphQLQuery.description}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="responsestrategy"><spring:message code="graphql.graphqlquery.label.responsestrategy"/></label></td>
                    <td class="column2"><label path="responsestrategy"><spring:message code="graphql.type.responsestrategy.${graphQLQuery.responseStrategy}"/></label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="status"><spring:message code="graphql.graphqlquery.label.status"/></label></td>
                    <td class="column2"><label path="status"><spring:message code="graphql.type.graphqloperationstatus.${graphQLQuery.status}"/></label></td>
                </tr>
            </table>
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="graphql.graphqlquery.header.arguments"/></h3>
            </div>
            <c:choose>
                <c:when test="${graphQLQuery.arguments.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover sortable">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                            <col width="30%">
                            <col width="40%">

                            <tr>
                                <th><spring:message code="graphql.graphqlquery.column.name"/></th>
                                <th><spring:message code="graphql.graphqlquery.column.type"/></th>
                                <th><spring:message code="graphql.graphqlquery.column.nullable"/></th>
                                <th><spring:message code="graphql.graphqlquery.column.defaultvalue"/></th>
                                <th><spring:message code="graphql.graphqlquery.column.description"/></th>
                            </tr>

                            <c:forEach items="${graphQLQuery.arguments}" var="graphQLArgument" varStatus="loopStatus">
                                <tr>
                                    <c:choose>
                                        <c:when test="${graphQLArgument.attributeType == 'OBJECT_TYPE'}">
                                            <td><a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/object/${graphQLArgument.typeId}"/>">${graphQLArgument.name}</a></td>
                                        </c:when>
                                        <c:when test="${graphQLArgument.attributeType == 'ENUM'}">
                                            <td><a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/enum/${graphQLArgument.typeId}"/>">${graphQLArgument.name}</a></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${graphQLArgument.name}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>
                                        <spring:message code="graphql.type.attributetype.${graphQLArgument.attributeType}"/>
                                        <c:choose>
                                            <c:when test="${graphQLArgument.listable == true}">
                                                []
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>${graphQLArgument.nullable}</td>
                                    <td>${graphQLArgument.defaultValue}</td>
                                    <td>${graphQLArgument.description}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <spring:message code="graphql.graphqlquery.label.noarguments"/>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="graphql.graphqlquery.header.result"/></h3>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-hover sortable">
                    <col width="90%">
                    <col width="10%">


                    <tr>
                        <th><spring:message code="graphql.graphqlquery.column.type"/></th>
                        <th><spring:message code="graphql.graphqlquery.column.nullable"/></th>
                    </tr>

                    <tr>
                        <c:choose>
                            <c:when test="${graphQLQuery.result.attributeType == 'OBJECT_TYPE'}">
                                <td><a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/object/${graphQLQuery.result.typeId}"/>">${graphQLQuery.result.typeName}</a></td>
                            </c:when>
                            <c:when test="${graphQLQuery.result.attributeType == 'ENUM'}">
                                <td><a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/enum/${graphQLQuery.result.typeId}"/>">${graphQLQuery.result.typeName}</a></td>
                            </c:when>
                            <c:otherwise>
                                <td>${graphQLQuery.result.attributeType}</td>
                            </c:otherwise>
                        </c:choose>
                        <td>${graphQLQuery.result.nullable}</td>
                    </tr>
                </table>
            </div>
        </div>
    </section>
</div>