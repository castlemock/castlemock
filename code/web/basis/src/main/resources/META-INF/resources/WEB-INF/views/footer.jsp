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
<footer>
    <div class="languages">
        <!--<a href="?language=en"><img class="flag flag-gb" alt="English"/></a>-->
    </div>

    <div class="info">
        <c:choose>
            <c:when test="${demoMode}">
                <a href="https://www.castlemock.com" target="_blank">Castle Mock version. ${appVersion} (Demo)</a>
            </c:when>
            <c:otherwise>
                <a href="https://www.castlemock.com" target="_blank">Castle Mock version. ${appVersion}</a>
            </c:otherwise>
        </c:choose>
    </div>
</footer>