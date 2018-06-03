<%--
 Copyright 2018 Karl Dahlgren

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
<div class="sideMenu">
    <a href="${context}/web/me">
        <div class="sideMenuItem sideMenuItemFirst sideMenuItemSelected">
            <div class="sideMenuItemContent sideMenuItemContentSelected">
                <div class="sideMenuItemContentIcon">
                    <i class="fa fa-tachometer fa-2x"></i>
                </div>
                <div class="sideMenuItemContentTitle">
                    <spring:message code="general.menu.home"/>
                </div>
            </div>
        </div>
    </a>
    <a href="${context}/web/me">
        <div class="sideMenuItem">
            <div class="sideMenuItemContent">
                <div class="sideMenuItemContentIcon">
                    <i class="fa fa-archive fa-2x"></i>
                </div>
                <div class="sideMenuItemContentTitle">
                    <spring:message code="general.menu.events"/>
                </div>
            </div>
        </div>
    </a>
    <sec:authorize access="hasAuthority('ADMIN')">
        <a href="${context}/web/me">
            <div class="sideMenuItem">
                <div class="sideMenuItemContent">
                    <div class="sideMenuItemContentIcon">
                        <i class="fa fa-users fa-2x"></i>
                    </div>
                    <div class="sideMenuItemContentTitle">
                        <spring:message code="general.menu.users"/>
                    </div>
                </div>
            </div>
        </a>
    </sec:authorize>
    <sec:authorize access="hasAuthority('ADMIN')">
    <a href="${context}/web/me">
        <div class="sideMenuItem">
            <div class="sideMenuItemContent">
                <div class="sideMenuItemContentIcon">
                    <i class="fa fa-cogs fa-2x"></i>
                </div>
                <div class="sideMenuItemContentTitle">
                    <spring:message code="general.menu.system"/>
                </div>
            </div>
        </div>
    </a>
    </sec:authorize>
</div>