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

<c:url var="create_graphql_graphql_url"  value="/web/graphql/project/${graphQLProjectId}/create/application" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}"><spring:message code="graphql.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="graphql.createapplication.header.application"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="graphql.createapplication.header.application"/></h1>
        </div>
        <form:form action="${create_graphql_graphql_url}" method="POST" modelAttribute="graphQLApplication">
            <table class="formTable">
                <tr>
                    <td class="column1"><label><spring:message code="graphql.createapplication.label.name"/></label></td>
                    <td class="column2"><form:input id="graphQLApplicationNameInput" path="name" /></td>
                </tr>
                <tr>
                    <td class="column1"><spring:message code="graphql.createapplication.label.description"/></td>
                    <td class="column2"><form:textarea rows="4" path="description"></form:textarea></td>
                </tr>
            </table>

            <button class="btn btn-success" type="submit" name="submit"><i class="fa fa-plus"></i> <span><spring:message code="graphql.createapplication.button.createapplication"/></span></button>
            <a href="<c:url value="/web/graphql/project/${graphQLProjectId}"/>" class="btn btn-danger"><i class="fa fa-times"></i> <span><spring:message code="graphql.createapplication.button.cancel"/></span></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
        <script>
            $("#graphQLApplicationNameInput").attr('required', '');
        </script>
    </section>
</div>