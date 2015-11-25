<%@ include file="includes.jspf"%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:url var="searchUrl"  value="/web/search" />
<spring:message code="general.header.input.search" var="searchPlaceholder"/>
<header class="main-header">
    <div class="logo">
        <a href="${context}/web"><img src="${context}/resources/images/logo-landscape-white.png" id="header-logo"/></a>
    </div>

    <div class="meta">
        <form:form action="${searchUrl}" method="POST" commandName="searchCommand">
            <form:input onfocus="this.placeholder = ''" type="text" path="query" placeholder="${searchPlaceholder}"></form:input>
        </form:form>
        <a href="${context}/web"><i class="fa fa-home fa-2x"></i> </a>
        <a href="${context}/web/me"><i class="fa fa-user fa-2x"></i> </a>
        <a href="${context}/web/event"><i class="fa fa-file-text fa-2x"></i> </a>
        <sec:authorize access="hasRole('ADMIN')"><a href="${context}/web/configuration"><i class="fa fa-cogs fa-2x"></i> </a></sec:authorize>
        <sec:authorize access="hasRole('ADMIN')"><a href="${context}/web/user"><i class="fa fa-users fa-2x"></i> </a></sec:authorize>
        <a href="${context}/web/logout"><i class="fa fa-sign-out fa-2x"></i> </a>
    </div>
</header>
