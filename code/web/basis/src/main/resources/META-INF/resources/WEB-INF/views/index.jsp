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
  <head>
      <title><spring:message code="general.index.title.title"/></title>
      <link rel="icon" href="<c:url value="/favicon.ico"/>" type="image/x-icon" />
      <link rel="stylesheet" href="<c:url value="/resources/css/pure/pure-min.css"/>">
      <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/css/font-awesome.css"/>">
      <link rel="stylesheet" href="<c:url value="/resources/css/main-stylesheet.css"/>">
      <link rel="stylesheet" href="<c:url value="/resources/css/flags/flags.css"/>">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/jquery/jquery-2.1.4.min.js"/>"></script>
      <script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/main-script.js"/>"></script>
      <script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/table.js"/>"></script>
  </head>
    <body>
        <jsp:include page="header.jsp" />
        <jsp:include page="navigation.jsp" />

        <div class="container">
            <section>
                    <jsp:include page="${partial}" />
            </section>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>