<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>



<spring:url value="/js/jquery.js" var="jqueryUrl" />
<script src="${jqueryUrl}"></script>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>


<meta charset="ISO-8859-1">
<title><tiles:insertAttribute name="title"
		defaultValue="Projects" defaultValueType="string" /></title>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" />
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/images/favicon.ico"
	type="image/x-icon">
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico"
	type="image/x-icon">

<!-- <!-- <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> -->
<!-- <!-- 	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script> -->
<!-- <!--     <script type="text/javascript"> -->
<!-- //       google.charts.load('current', {'packages':['bar']}); -->
<!-- //       google.charts.setOnLoadCallback(drawChart); -->
<!-- //       function drawChart() { -->
<!-- //         var data = google.visualization.arrayToDataTable([ -->
<!-- //           ['Task', 'Hours'], -->
<%-- //           <c:forEach items="${tasks}" var="task" varStatus="status"> --%>
<%-- //          var name = ${task.taskName}; --%>
<%-- //          var hour = ${totalHourArray[status.index]}; --%>
<!-- //          [name , 10], -->
<%-- //      	 </c:forEach>]); --%>

<!-- //         var options = { -->
<!-- //           chart: { -->
<!-- //             title: 'Company Performance', -->
<!-- //             subtitle: 'Sales, Expenses, and Profit: 2014-2017', -->
<!-- //           }, -->
<!-- //           bars: 'horizontal' // Required for Material Bar Charts. -->
<!-- //         }; -->

<!-- //         var chart = new google.charts.Bar(document.getElementById('barchart_material')); -->

<!-- //         chart.draw(data, options); -->
<!-- //       } -->
<!-- <!--     </script> -->

</head>
<body>
	<div class="container">
		<nav>
			<ul class="nav nav-pills pull-right">
				<li role="presentation"><a href="../index.html">Home</a></li>

				<li role="presentation"><security:authorize access="anonymous">
						<a href="<c:url value='/login.html' />">Login</a>
					</security:authorize></li>


				<security:authorize access="authenticated">
					<security:authorize access="hasRole('ADMIN')">

						<li role="presentation"><a href="../admin/listProjects.html">Project
								Management</a></li>
						<li role="presentation"><a
							href="../admin/userManagement.html">User Management</a></li>
					</security:authorize>
					<security:authorize access="hasAnyRole('MANAGER')">

						<li role="presentation"><a
							href="../manager/listProjects.html">Project Management</a></li>


					</security:authorize>
					<security:authorize access="hasRole('MEMBER')">
						<li role="presentation"><a href="../member/listProjects.html">Projects
								For You</a></li>
					</security:authorize>

					<li role="presentation"><a href="<c:url value='/logout' />">Logout</a></li>
				</security:authorize>
			</ul>
		</nav>
		<security:authorize access="authenticated">
			<h3 class="text-muted">
				Welcome,
				<security:authentication property="principal.username" />
			</h3>
		</security:authorize>
		<div class="content">
			<tiles:insertAttribute name="content" />
		</div>
		<jsp:include page="/WEB-INF/jsp/footer.jsp" />
	</div>



	<%-- <security:authorize access="authenticated">
		<p>
			<a href="<c:url value='/logout' />">Logout</a>
		</p>
	</security:authorize>

	<p>Copyright &copy; 2016, Chengyu Sun. All rights reserved.</p> --%>

</body>
</html>
