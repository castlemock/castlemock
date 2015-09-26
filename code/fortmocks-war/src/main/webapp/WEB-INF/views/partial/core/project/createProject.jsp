<%@ include file="../../../includes.jspf"%>
<c:url var="create_project_url"  value="/web/project/create" />
<div class="content-top">
    <h1><spring:message code="general.createproject.header"/></h1>
</div>
<form:form action="${create_project_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><label><spring:message code="general.createproject.label.name"/></label></td>
            <td class="column2"><form:input path="project.name" />
        </tr>
        <tr>
            <td class="column1"><spring:message code="general.createproject.label.description"/></td>
            <td class="column2"><form:textarea rows="4" path="project.description"></form:textarea></td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="general.createproject.label.projecttype"/></td>
            <td>
                <form:select path="projectType">
                    <c:forEach items="${projectTypes}" var="projectType">
                        <option value="${projectType}">${projectType}</option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="general.createproject.label.domainnamestrategies"/></td>
            <td>
                <form:select path="project.domainNameStrategy">
                    <c:forEach items="${domainNameStrategies}" var="domainNameStrategy">
                        <option value="${domainNameStrategy}">${domainNameStrategy.strategy}</option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
    </table>
 
    <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i> <span><spring:message code="general.createproject.button.createproject"/></span></button>
    <a href="<c:url value="/web"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="general.createproject.button.cancel"/></span></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>