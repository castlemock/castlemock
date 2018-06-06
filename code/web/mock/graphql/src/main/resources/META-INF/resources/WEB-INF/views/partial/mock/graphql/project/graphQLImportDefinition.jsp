<%@ include file="../../../../includes.jspf"%>
<%--
  ~ Copyright 2015 Karl Dahlgren
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

<c:url var="import_url"  value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}/import" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}"><spring:message code="graphql.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}"><spring:message code="graphql.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="graphql.graphqlimportdefinition.header.uploadfile" arguments="${definitionDisplayName}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="graphql.graphqlimportdefinition.header.uploadfile" arguments="${definitionDisplayName}"/></h1>
        </div>

        <div class="upload-information">
            <i class="fas fa-info-circle fa-4x"></i>
            <p>
                <spring:message code="graphql.graphqlimportdefinition.header.description" arguments="${definitionDisplayName}"/>
            </p>
        </div>

        <div class="upload-link">
            <h2 class="decorated"><span><spring:message code="graphql.graphqlimportdefinition.header.link" arguments="${definitionDisplayName}"/></span></h2>
            <form:form method="POST" action="${import_url}" modelAttribute="uploadForm">
                <table class="formTable">
                    <tr>
                        <td class="column1"><form:label path="link"><spring:message code="graphql.graphqlimportdefinition.label.link"  arguments="${definitionDisplayName}"/></form:label></td>
                        <td class="column2"><form:input class="form-control" path="link" type="text" name="linkInput" id="linkInput" size="100"/>
                    </tr>
                </table>
                <button class="btn btn-success" type="submit" name="type" value="link"><i class="fas fa-upload"></i> <span><spring:message code="graphql.graphqlimportdefinition.button.link" arguments="${definitionDisplayName}"/></span></button>
                <input type="hidden" name="definitionType" value="${definitionType}"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </div>

        <h2 class="decorated"><span><spring:message code="graphql.graphqlimportdefinition.header.upload" arguments="${definitionDisplayName}"/></span></h2>

        <div class="upload-file">
            <form:form method="POST" enctype="multipart/form-data" action="${import_url}" modelAttribute="uploadForm">
                <input type="file" id="files" name="files"/>

                <div id="messages">
                </div>

                <button class="btn btn-success" name="type" value="file"><i class="fas fa-upload"></i> <span><spring:message code="graphql.graphqlimportdefinition.button.uploadfiles"/></span></button>
                <a href="<c:url value="/web/graphql/project/${graphQLProjectId}/application/${graphQLApplicationId}"/>" class="btn btn-primary"><i class="fas fa-times"></i> <spring:message code="graphql.graphqlimportdefinition.button.cancel"/></a>
                <input type="hidden" name="definitionType" value="${definitionType}"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </div>
    </section>
</div>

<script src=<c:url value="/resources/js/FileUpload.js"/>>
</script>
<script>
    $("#linkInput").attr('required', '');
</script>