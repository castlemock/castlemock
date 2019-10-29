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
        <li class="active"><spring:message code="general.system.header.system"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="general.system.header.system"/></h1>
            </div>
        </div>
        <table class="table">
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.operatingsystemname"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.operatingSystemName}</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.javaversion"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.javaVersion}</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.javavendor"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.javaVendor}</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.serverinfo"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.tomcatInfo}</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.serverbuilt"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.tomcatBuilt}</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.serverversion"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.tomcatVersion}</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.totalmemory"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.totalMemory}  MB</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.maxmemory"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.maxMemory}  MB</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.freememory"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.freeMemory} MB</label></td>
            </tr>
            <tr>
                <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.availableprocessors"/></label></td>
                <td class="column2"><label path="javaVersion">${systemInformation.availableProcessors}</label></td>
            </tr>
            <c:if test="${systemInformation.showCastleMockHomeDirectory}">
                <tr>
                    <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.castlemockhomedirectory"/></label></td>
                    <td class="column2"><label path="javaVersion">${systemInformation.castleMockHomeDirectory}</label></td>
                </tr>
            </c:if>
            <c:if test="${systemInformation.showMongoProperties}">
                <c:if test="${systemInformation.mongoProperties.usesUri == false}">
                    <tr>
                        <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.mongo.host"/></label></td>
                        <td class="column2"><label path="javaVersion">${systemInformation.mongoProperties.host}</label></td>
                    </tr>
                    <tr>
                        <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.mongo.port"/></label></td>
                        <td class="column2"><label path="javaVersion">${systemInformation.mongoProperties.port}</label></td>
                    </tr>
                </c:if>
                <c:if test="${systemInformation.mongoProperties.usesUri}">
                    <tr>
                        <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.mongo.uri"/></label></td>
                        <td class="column2"><label path="javaVersion">${systemInformation.mongoProperties.uri}</label></td>
                    </tr>
                </c:if>
                <tr>
                    <td class="column1"><label path="javaVersion"><spring:message code="general.system.label.mongo.database"/></label></td>
                    <td class="column2"><label path="javaVersion">${systemInformation.mongoProperties.database}</label></td>
                </tr>
            </c:if>
        </table>
    </section>
</div>

