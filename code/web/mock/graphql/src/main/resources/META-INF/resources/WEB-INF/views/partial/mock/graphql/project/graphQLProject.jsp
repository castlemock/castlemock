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

<c:url var="graphql_resource_update_url"  value="/web/graphql/project/${graphQLProject.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li class="active"><spring:message code="graphql.graphqlproject.header.project" arguments="${graphQLProject.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <c:if test="${not empty upload}">
            <c:if test="${upload == 'success'}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong><spring:message code="graphql.graphqlimportdefinition.message.success.title"/></strong> <spring:message code="graphql.graphqlimportdefinition.message.success.body"/>
                </div>
            </c:if>
            <c:if test="${upload == 'error'}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong><spring:message code="graphql.graphqlimportdefinition.message.error.title"/></strong> <spring:message code="graphql.graphqlimportdefinition.message.error.body"/>
                </div>
            </c:if>
        </c:if>

        <div class="content-top">
            <div class="title">
                <h1><spring:message code="graphql.graphqlproject.header.project" arguments="${graphQLProject.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled"  href="<c:url value="/web/graphql/project/${graphQLProject.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="graphql.graphqlproject.button.updateproject"/></span></a>
                    <div class="btn-group">
                        <button type="button" class="btn btn-primary dropdown-toggle demo-button-disabled" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-upload"></i> <span><spring:message code="graphql.graphqlproject.button.upload"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/web/graphql/project/${graphQLProject.id}/import?type=SCHEMA"/>">Schema</a></li>
                        </ul>
                    </div>
                    <a class="btn btn-primary" href="<c:url value="/web/graphql/project/${graphQLProject.id}/export"/>"><i class="fa fa-cloud-download"></i> <span><spring:message code="graphql.graphqlproject.button.export"/></span></a>
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/graphql/project/${graphQLProject.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="graphql.graphqlproject.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="graphql.graphqlproject.label.name"/></label></td>
                    <td class="column2"><label path="name">${graphQLProject.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="description"><spring:message code="graphql.graphqlproject.label.description"/></label></td>
                    <td class="column2"><label path="description">${graphQLProject.description}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="projectType"><spring:message code="graphql.graphqlproject.label.type"/></label></td>
                    <td class="column2"><label path="projectType">GraphQL</label></td>
                </tr>
            </table>
        </div>


        <h2 class="decorated"><span><spring:message code="graphql.graphqlproject.header.operations"/></span></h2>
        <c:choose>
            <c:when test="${graphQLProject.queries.size() > 0 || graphQLProject.mutations.size() > 0 || graphQLProject.subscriptions.size() > 0}">
                <form:form action="${graphql_resource_update_url}/" method="POST"  commandName="graphQLQueryModifierCommand">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover sortable">
                            <col width="10%">
                            <col width="10%">
                            <col width="40%">

                            <tr>
                                <th><spring:message code="graphql.graphqlproject.column.selected"/></th>
                                <th><spring:message code="graphql.graphqlproject.column.name"/></th>
                                <th><spring:message code="graphql.graphqlproject.column.type"/></th>
                                <th><spring:message code="graphql.graphqlproject.column.description"/></th>
                            </tr>

                            <c:forEach items="${graphQLProject.queries}" var="graphQLQuery" varStatus="loopStatus">
                                <tr>
                                    <td><form:checkbox path="graphQLQueriesIds" name="${graphQLQuery.id}" value="${graphQLQuery.id}"/></td>
                                    <td><a href="<c:url value="/web/graphql/project/${graphQLProject.id}/query/${graphQLQuery.id}"/>">${graphQLQuery.name}</a></td>
                                    <td><spring:message code="graphql.graphqlproject.column.type.query"/></td>
                                    <td>${graphQLQuery.description}</td>
                                </tr>
                            </c:forEach>

                            <c:forEach items="${graphQLProject.mutations}" var="graphQLMutation" varStatus="loopStatus">
                                <tr>
                                    <td><form:checkbox path="graphQLQueriesIds" name="${graphQLMutation.id}" value="${graphQLMutation.id}"/></td>
                                    <td><a href="<c:url value="/web/graphql/project/${graphQLProject.id}/mutation/${graphQLMutation.id}"/>">${graphQLMutation.name}</a></td>
                                    <td><spring:message code="graphql.graphqlproject.column.type.mutation"/></td>
                                    <td>${graphQLQuery.description}</td>
                                </tr>
                            </c:forEach>

                            <c:forEach items="${graphQLProject.subscriptions}" var="graphQLSubscription" varStatus="loopStatus">
                                <tr>
                                    <td><form:checkbox path="graphQLQueriesIds" name="${graphQLSubscription.id}" value="${graphQLSubscription.id}"/></td>
                                    <td><a href="<c:url value="/web/graphql/project/${graphQLProject.id}/subscription/${graphQLSubscription.id}"/>">${graphQLSubscription.name}</a></td>
                                    <td><spring:message code="graphql.graphqlproject.column.type.subscription"/></td>
                                    <td>${graphQLQuery.description}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form:form>
            </c:when>
            <c:otherwise>
                <spring:message code="graphql.graphqlproject.label.nooperations"/>
            </c:otherwise>
        </c:choose>

        <h2 class="decorated"><span><spring:message code="graphql.graphqlproject.header.types"/></span></h2>
        <c:choose>
            <c:when test="${graphQLProject.objects.size() > 0 || graphQLProject.enums.size() > 0}">
                <div class="table-responsive">
                    <table class="table table-striped table-hover sortable">
                        <col width="10%">
                        <col width="10%">
                        <col width="40%">

                        <tr>
                            <th><spring:message code="graphql.graphqlproject.column.name"/></th>
                            <th><spring:message code="graphql.graphqlproject.column.type"/></th>
                            <th><spring:message code="graphql.graphqlproject.column.description"/></th>
                        </tr>

                        <c:forEach items="${graphQLProject.objects}" var="graphQLObject" varStatus="loopStatus">
                            <tr>
                                <td><a href="<c:url value="/web/graphql/project/${graphQLProject.id}/object/${graphQLObject.id}"/>">${graphQLObject.name}</a></td>
                                <td><spring:message code="graphql.graphqlproject.column.type.object"/></td>
                                <td>${graphQLObject.description}</td>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${graphQLProject.enums}" var="graphQLEnum" varStatus="loopStatus">
                            <tr>
                                <td><a href="<c:url value="/web/graphql/project/${graphQLProject.id}/enum/${graphQLEnum.id}"/>">${graphQLEnum.name}</a></td>
                                <td><spring:message code="graphql.graphqlproject.column.type.enum"/></td>
                                <td>${graphQLEnum.description}</td>
                            </tr>
                        </c:forEach>

                    </table>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </c:when>
            <c:otherwise>
                <spring:message code="graphql.graphqlproject.label.nooperations"/>
            </c:otherwise>
        </c:choose>

    </section>
</div>