<%@ include file="includes.jspf"%>
<aside>
  <div class="content">
    <c:choose>
      <c:when test="${contentItemGroups.size() > 0}">
        <c:forEach items="${contentItemGroups}" var="contentItemGroup" varStatus="loopStatus">
          <c:choose>
            <c:when test="${contentItemGroup.contentItems.size() > 0}">
              <div class="content-title">
                ${contentItemGroup.title}
              </div>
              <div class="content-divider"></div>
              <c:forEach items="${contentItemGroup.contentItems}" var="contentItem" varStatus="loopStatus">
                <c:choose>
                  <c:when test="${contentItem.role eq 'READER'}">
                    <div class="content-item">
                      <a href="${contentItem.url}"><i class="${contentItem.icon}"></i> ${contentItem.title}</a>
                    </div>
                  </c:when>
                  <c:when test="${contentItem.role eq 'MODIFIER'}">
                    <sec:authorize access="hasRole('MODIFIER') or hasRole('ADMIN')">
                      <div class="content-item">
                        <a href="${contentItem.url}"><i class="${contentItem.icon}"></i> ${contentItem.title}</a>
                      </div>
                    </sec:authorize>
                  </c:when>
                  <c:when test="${contentItem.role eq 'ADMIN'}">
                    <sec:authorize access="hasRole('ADMIN')">
                      <div class="content-item">
                        <a href="${contentItem.url}"><i class="${contentItem.icon}"></i> ${contentItem.title}</a>
                      </div>
                    </sec:authorize>
                  </c:when>
                </c:choose>
              </c:forEach>
            </c:when>
          </c:choose>
        </c:forEach>
      </c:when>
    </c:choose>
  </div>
</aside>