<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="url" required="true" rtexprvalue="true"%>
<c:url value="/${url}" var="fullUrl"/>
<![CDATA[<script src="${fullUrl}"></script>]]>