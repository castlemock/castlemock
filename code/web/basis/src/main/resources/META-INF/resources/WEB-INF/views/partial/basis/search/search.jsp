<%@ include file="../../../includes.jspf"%>

<div class="content-top">
    <h1><spring:message code="general.search.header.results"/></h1>
</div>
<c:choose>
    <c:when test="${searchResults.size() > 0}">
        <div class="table-frame">
            <table class="entityTable">
                <tr>
                    <th><spring:message code="general.search.column.title"/></th>
                </tr>
                <c:forEach items="${searchResults}" var="searchResult" varStatus="loopStatus">
                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                        <td><a href="<c:url value="/web/${searchResult.link}"/>">${searchResult.title}</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <spring:message code="general.search.label.noresults"/>
    </c:otherwise>
</c:choose>