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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/event"><spring:message code="general.breadcrumb.logs"/></a></li>
        <li class="active"><spring:message code="soap.event.header.log" arguments="${event.id}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="soap.event.header.log" arguments="${event.id}"/></h1>
        </div>

        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.event.column.id"/></label></td>
                    <td class="column2"><label path="name">${event.id}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.event.column.startdate"/></label></td>
                    <td class="column2"><label path="name">${event.startDate}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.event.column.enddate"/></label></td>
                    <td class="column2"><label path="name">${event.endDate}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.event.column.request.uri"/></label></td>
                    <td class="column2"><label path="name">${event.request.uri}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.event.column.request.methodtype"/></label></td>
                    <td class="column2"><label path="name">${event.request.httpMethod}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.event.column.request.type"/></label></td>
                    <td class="column2"><label path="name">${event.request.soapVersion}</label></td>
                </tr>
            </table>
        </div>

        <div class="panel with-nav-tabs panel-primary">
            <div class="panel-heading">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#request"><spring:message code="soap.event.header.request"/></a></li>
                    <li><a data-toggle="tab" href="#response"><spring:message code="soap.event.header.response"/></a></li>
                </ul>
            </div>
            <div class="panel-body">
                <div class="tab-content">
                    <div id="request" class="tab-pane fade in active">
                        <h3><span><spring:message code="soap.event.header.body"/></span></h3>
                        <div class="eventMessage">
                            <pre><code class="xml"><c:out value="${event.request.body}"/></code></pre>
                        </div>
                        <c:choose>
                            <c:when test="${event.request.httpHeaders.size() > 0}">
                                <h3><span><spring:message code="soap.event.header.headers"/></span></h3>
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover sortable">
                                        <tr>
                                            <th><spring:message code="soap.event.column.headername"/></th>
                                            <th><spring:message code="soap.event.column.headervalue"/></th>
                                        </tr>
                                        <c:forEach items="${event.request.httpHeaders}" var="httpHeader" varStatus="loopStatus">
                                            <tr>
                                                <td>${httpHeader.name}</td>
                                                <td>${httpHeader.value}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>

                    <div id="response" class="tab-pane fade">
                        <table class="formTable">
                            <tr>
                                <td class="column1"><label path="name"><spring:message code="soap.event.column.response.statuscode"/></label></td>
                                <td class="column2"><label path="name">${event.response.httpStatusCode}</label></td>
                            </tr>
                        </table>
                        <h3><span><spring:message code="soap.event.header.body"/></span></h3>
                        <div class="eventMessage">
                            <pre><code id="responseBody" class="xml"><c:out value="${event.response.body}"/></code></pre>
                        </div>

                        <c:choose>
                            <c:when test="${event.response.httpHeaders.size() > 0}">
                                <h3><span><spring:message code="soap.event.header.headers"/></span></h3>
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover sortable">
                                        <tr>
                                            <th><spring:message code="soap.event.column.headername"/></th>
                                            <th><spring:message code="soap.event.column.headervalue"/></th>
                                        </tr>
                                        <c:forEach items="${event.response.httpHeaders}" var="httpHeader" varStatus="loopStatus">
                                            <tr>
                                                <td>${httpHeader.name}</td>
                                                <td>${httpHeader.value}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>