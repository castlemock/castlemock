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
<html>
<body>
	<h1><spring:message code="general.error.forbidden.title"/></h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2><spring:message code="general.error.forbidden.description"/></h2>
		</c:when>
		<c:otherwise>
			<h2><spring:message code="general.error.forbidden.user" arguments="${username}"/> <br/><spring:message code="general.error.forbidden.description"/></h2>
		</c:otherwise>
	</c:choose>

</body>
</html>