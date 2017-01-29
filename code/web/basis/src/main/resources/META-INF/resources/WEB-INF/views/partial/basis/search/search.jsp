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

<div class="content-top">
    <h1><spring:message code="general.search.header.results"/></h1>
</div>
<c:choose>
    <c:when test="${searchResults.size() > 0}">
        <div class="table-frame">
            <table class="entityTable sortable">
                <col width="50%">
                <col width="50%">
                <tr>
                    <th><spring:message code="general.search.column.title"/></th>
                    <th><spring:message code="general.search.column.description"/></th>
                </tr>
                <c:forEach items="${searchResults}" var="searchResult" varStatus="loopStatus">
                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                        <td><a href="<c:url value="/web/${searchResult.link}"/>">${searchResult.title}</a></td>
                        <td>${searchResult.description}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <spring:message code="general.search.label.noresults"/>
    </c:otherwise>
</c:choose>