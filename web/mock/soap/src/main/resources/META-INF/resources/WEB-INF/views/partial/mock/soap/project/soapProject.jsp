<%--
 Copyright 2016 Karl Dahlgren

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%@ include file="../../../../includes.jspf"%>

<c:url var="soap_port_update_url"  value="/web/soap/project/${soapProject.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li class="active"><spring:message code="soap.soapproject.header.project" arguments="${soapProject.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <c:if test="${not empty upload}">
            <c:if test="${upload == 'success'}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong><spring:message code="soap.soapaddwsdl.message.success.title"/></strong> <spring:message code="soap.soapaddwsdl.message.success.body"/>
                </div>
            </c:if>
            <c:if test="${upload == 'error'}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong><spring:message code="soap.soapaddwsdl.message.error.title"/></strong> <spring:message code="soap.soapaddwsdl.message.error.body"/>
                </div>
            </c:if>
        </c:if>


        <div class="content-top">
            <div class="title">
                <h1><spring:message code="soap.soapproject.header.project" arguments="${soapProject.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled" href="<c:url value="/web/soap/project/${soapProject.id}/update"/>"><i class="fas fa-edit"></i> <span><spring:message code="soap.soapproject.button.updateproject"/></span></a>
                    <div class="btn-group">
                        <button type="button" class="btn btn-primary dropdown-toggle demo-button-disabled" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-upload"></i> <span><spring:message code="soap.soapproject.button.upload"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/web/soap/project/${soapProject.id}/add/wsdl"/>">WSDL</a></li>
                        </ul>
                    </div>
                    <a class="btn btn-primary" href="<c:url value="/web/soap/project/${soapProject.id}/export"/>"><i class="fas fa-cloud-download-alt"></i> <span><spring:message code="soap.soapproject.button.export"/></span></a>
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/soap/project/${soapProject.id}/delete"/>"><i class="fas fa-trash"></i> <span><spring:message code="soap.soapproject.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="description"><spring:message code="soap.soapproject.label.description"/></label></td>
                    <td class="column2"><label path="description">${soapProject.description}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="projectType"><spring:message code="soap.soapproject.label.type"/></label></td>
                    <td class="column2"><label path="projectType">SOAP</label></td>
                </tr>
            </table>
        </div>

            <div class="panel panel-primary table-panel">
                <div class="panel-heading table-panel-heading">
                    <h3 class="panel-title"><spring:message code="soap.soapproject.header.ports"/></h3>
                </div>
                <c:choose>
                    <c:when test="${soapProject.ports.size() > 0}">
                        <form:form action="${soap_port_update_url}/" method="POST"  modelAttribute="command">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover sortable">
                                    <col width="5%">
                                    <col width="40%">
                                    <tr>
                                        <th></th>
                                        <th><spring:message code="soap.soapproject.column.port"/></th>
                                        <c:forEach items="${soapOperationStatuses}" var="soapOperationStatus">
                                            <th><spring:message code="soap.type.soapoperationstatus.${soapOperationStatus}"/></th>
                                        </c:forEach>
                                    </tr>
                                    <c:forEach items="${soapProject.ports}" var="soapPort" varStatus="loopStatus">
                                        <tr>
                                            <td><form:checkbox path="soapPortIds" name="${soapPort.id}" value="${soapPort.id}"/></td>
                                            <td><a href="<c:url value="/web/soap/project/${soapProject.id}/port/${soapPort.id}"/>">${soapPort.name}</a></td>
                                            <c:forEach items="${soapOperationStatuses}" var="soapOperationStatus">
                                                <td>${soapPort.statusCount[soapOperationStatus]}</td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                            <div class="panel-buttons">
                                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                                    <form:select path="soapPortStatus">
                                        <c:forEach items="${soapOperationStatuses}" var="soapOperationStatus">
                                            <form:option value="${soapOperationStatus}"><spring:message code="soap.type.soapoperationstatus.${soapOperationStatus}"/></form:option>
                                        </c:forEach>
                                    </form:select>
                                    <button class="btn btn-success demo-button-disabled" type="submit" name="action" value="update"><i class="fas fa-check-circle"></i> <span><spring:message code="soap.soapproject.button.update"/></span></button>
                                    <button class="btn btn-primary demo-button-disabled" type="submit" name="action" value="update-endpoint"><i class="fas fa-project-diagram"></i> <span><spring:message code="soap.soapproject.button.updateendpoint"/></span></button>
                                    <button class="btn btn-danger demo-button-disabled" type="submit" name="action" value="delete"><i class="fas fa-trash"></i> <span><spring:message code="soap.soapproject.button.deleteport"/></span></button>
                                </sec:authorize>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>

                    </c:when>
                    <c:otherwise>
                        <spring:message code="soap.soapproject.label.noports" arguments="wsdl"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="panel panel-primary table-panel">
                <div class="panel-heading table-panel-heading">
                    <h3 class="panel-title"><spring:message code="soap.soapproject.header.resources"/></h3>
                </div>
                <c:choose>
                    <c:when test="${soapProject.resources.size() > 0}">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover sortable">
                                <col width="0%">
                                <col width="90%">
                                <tr>
                                    <th><spring:message code="soap.soapproject.column.type"/></th>
                                    <th><spring:message code="soap.soapproject.column.resource"/></th>
                                </tr>
                                <c:forEach items="${soapProject.resources}" var="soapResource" varStatus="loopStatus">
                                    <tr>
                                        <td>${soapResource.type}</td>
                                        <td><a href="<c:url value="/web/soap/project/${soapProject.id}/resource/${soapResource.id}"/>">${soapResource.name}</a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="soap.soapproject.label.noresources" arguments="wsdl"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </section>
</div>