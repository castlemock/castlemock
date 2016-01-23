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