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

<c:url var="graphql_resource_update_url"  value="/web/graphql/project/${graphQLEnumType.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}"><spring:message code="graphql.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}"><spring:message code="graphql.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="graphql.graphqlenum.header.enum" arguments="${graphQLEnumType.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="graphql.graphqlenum.header.enum" arguments="${graphQLEnumType.name}"/></h1>
            </div>
            <div class="menu" align="right">

            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="graphql.graphqlenum.label.name"/></label></td>
                    <td class="column2"><label path="name">${graphQLEnumType.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="description"><spring:message code="graphql.graphqlenum.label.description"/></label></td>
                    <td class="column2"><label path="description">${graphQLEnumType.description}</label></td>
                </tr>
            </table>
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="graphql.graphqlenum.header.definitions"/></h3>
            </div>
            <c:choose>
                <c:when test="${graphQLEnumType.definitions.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover sortable">
                            <col width="100%">
                            <tr>
                                <th><spring:message code="graphql.graphqlenum.column.name"/></th>
                            </tr>

                            <c:forEach items="${graphQLEnumType.definitions}" var="graphQLDefinition" varStatus="loopStatus">
                                <tr>
                                    <td>${graphQLDefinition.name}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <spring:message code="graphql.graphqlenum.label.nodefinitions"/>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>