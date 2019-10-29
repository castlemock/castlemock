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

<%@ include file="../../../includes.jspf"%>
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li class="active"><spring:message code="general.breadcrumb.logs"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="general.eventoverview.header.events"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')"><a class="btn btn-danger" href="<c:url value="/web/event/clear"/>"><i class="fas fa-trash"></i> <span><spring:message code="general.eventoverview.button.clearlogs"/></span></a></sec:authorize>
            </div>
        </div>
        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="general.eventoverview.header.log"/></h3>
            </div>
            <table class="table table-striped table-hover sortable">
                <col width="10%">
                <col width="30%">
                <col width="10%">
                <col width="25%">
                <col width="25%">
                <tr>
                    <th><spring:message code="general.eventoverview.column.id"/></th>
                    <th><spring:message code="general.eventoverview.column.resourcename"/></th>
                    <th><spring:message code="general.eventoverview.column.type"/></th>
                    <th><spring:message code="general.eventoverview.column.startdate"/></th>
                    <th><spring:message code="general.eventoverview.column.enddate"/></th>
                </tr>
                <c:forEach items="${events}" var="event" varStatus="loopStatus">
                    <tr>
                        <td><a href="<c:url value="/web/${event.typeIdentifier.typeUrl}/event/${event.id}"/>">${event.id}</a></td>
                        <td><a href="<c:url value="${event.resourceLink}"/>">${event.resourceName}</a></td>
                        <td><a href="<c:url value="/web/${event.typeIdentifier.typeUrl}/event/${event.id}"/>">${event.typeIdentifier.type}</a></td>
                        <td><a href="<c:url value="/web/${event.typeIdentifier.typeUrl}/event/${event.id}"/>">${event.startDate}</a></td>
                        <td><a href="<c:url value="/web/${event.typeIdentifier.typeUrl}/event/${event.id}"/>">${event.endDate}</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</div>