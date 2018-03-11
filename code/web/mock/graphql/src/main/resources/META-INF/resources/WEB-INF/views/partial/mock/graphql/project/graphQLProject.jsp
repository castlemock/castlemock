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
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="graphql.graphqlproject.header.project" arguments="${graphQLProject.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled"  href="<c:url value="/web/graphql/project/${graphQLProject.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="graphql.graphqlproject.button.updateproject"/></span></a>
                    <a class="btn btn-primary demo-button-disabled" href="<c:url value="/web/graphql/project/${graphQLProject.id}/create/application"/>"><i class="fa fa-plus"></i> <span><spring:message code="graphql.graphqlproject.button.createapplication"/></span></a>
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


        <h2 class="decorated"><span><spring:message code="graphql.graphqlproject.header.applications"/></span></h2>
        <c:choose>
            <c:when test="${graphQLProject.applications.size() > 0}">
                <form:form action="${graphql_resource_update_url}/" method="POST"  commandName="graphQLApplicationModifierCommand">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover sortable">
                            <col width="10%">
                            <col width="10%">
                            <col width="40%">

                            <tr>
                                <th><spring:message code="graphql.graphqlproject.column.selected"/></th>
                                <th><spring:message code="graphql.graphqlproject.column.name"/></th>
                                <th><spring:message code="graphql.graphqlproject.column.description"/></th>
                            </tr>

                            <c:forEach items="${graphQLProject.applications}" var="graphQLApplication" varStatus="loopStatus">
                                <tr>
                                    <td><form:checkbox path="graphQLApplicationIds" name="${graphQLApplication.id}" value="${graphQLApplication.id}"/></td>
                                    <td><a href="<c:url value="/web/graphql/project/${graphQLProject.id}/application/${graphQLApplication.id}"/>">${graphQLApplication.name}</a></td>
                                    <td>${graphQLApplication.description}</td>
                                </tr>
                            </c:forEach>

                        </table>
                    </div>
                    <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                        <button class="btn btn-danger demo-button-disabled" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="graphql.graphqlproject.button.deleteapplication"/></span></button>
                    </sec:authorize>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form:form>
            </c:when>
            <c:otherwise>
                <spring:message code="graphql.graphqlproject.label.noapplications"/>
            </c:otherwise>
        </c:choose>

    </section>
</div>