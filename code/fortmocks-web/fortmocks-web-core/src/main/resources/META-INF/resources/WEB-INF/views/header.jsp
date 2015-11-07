<%@ include file="includes.jspf"%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<header class="main-header">
    <div class="logo">
        <a href="${context}/web"><img src="${context}/images/logo-landscape-white.png" id="header-logo"/></a>
    </div>

    <div class="meta">
        <a href="${context}/web"><i class="fa fa-home fa-2x"></i> </a>
        <a href="${context}/web/me"><i class="fa fa-user fa-2x"></i> </a>
        <a href="${context}/web/event"><i class="fa fa-file-text fa-2x"></i> </a>
        <sec:authorize access="hasRole('ADMIN')"><a href="${context}/web/configuration"><i class="fa fa-cogs fa-2x"></i> </a></sec:authorize>
        <sec:authorize access="hasRole('ADMIN')"><a href="${context}/web/user"><i class="fa fa-users fa-2x"></i> </a></sec:authorize>
        <a href="${context}/web/logout"><i class="fa fa-sign-out fa-2x"></i> </a>
        <input placeholder="<spring:message code="general.header.input.search"/>" onfocus="this.placeholder = ''" type="text">
    </div>
</header>
