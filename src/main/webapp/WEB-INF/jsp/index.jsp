<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {

		var data = new google.visualization.DataTable();
		data.addColumn('date', 'Time of Day');
		data.addColumn('number', 'Rating');

		data.addRows([
				<c:forEach var="c" items="${list}" >[<c:out value="${c.key}"/>,
						<c:out value="${c.value}"/>], </c:forEach> ]);

		var options = {
			title : 'Rate the Day on a Scale of 1 to 10',
			width : 900,
			height : 500,
			hAxis : {
				format : 'M/d/yy',
				gridlines : {
					count : 15
				}
			},
			vAxis : {
				gridlines : {
					color : 'none'
				},
				minValue : 0
			}
		};

		var chart = new google.visualization.LineChart(document
				.getElementById('chart_div'));

		chart.draw(data, options);

		var button = document.getElementById('change');

		button.onclick = function() {

			// If the format option matches, change it to the new option,
			// if not, reset it to the original format.
			options.hAxis.format === 'M/d/yy' ? options.hAxis.format = 'MMM dd, yyyy'
					: options.hAxis.format = 'M/d/yy';

			chart.draw(data, options);
		};
	}
</script>
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		var x = $
		{
			complete
		}
		;
		var y = $
		{
			remaining
		}
		;
		var data = google.visualization.arrayToDataTable([
				[ 'Task', 'Counts' ], [ 'Completed Task', x ],
				[ 'Ongoing Task', y ] ]);

		var options = {
			title : 'Workload'
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('piechart'));

		chart.draw(data, options);
	}
</script>
<title>SpiderHub</title>

<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/images/favicon.ico"
	type="image/x-icon">
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico"
	type="image/x-icon">
</head>

<body>

	<div class="container">

		<jsp:include page="/WEB-INF/jsp/menu.jsp" />
		<div class="jumbotron">
			<h1>SpiderHub</h1>
			<p class="lead">Competition makes us faster, Collaboration makes
				us Better.</p>
			<p>

				<security:authorize access="anonymous">
					<a class="btn btn-lg btn-success" href="userRegistration.html"
						role="button">Sign up today</a>
				</security:authorize>
			</p>

			<security:authorize access="hasRole('MEMBER')">
				<div id="piechart" style="width: 500px; height: 300px"></div>
				<div id="chart_div" style="width: 100px; height: 100px"></div>
				<h2>High Priority Task</h2>
				<table name="High Priority Task" class="table table-hover">
					<tr>
						<th>Task Name</th>
						<th>Task Deadline</th>

					</tr>
					<c:forEach items="${high}" var="h">
						<tr>
							<td>${h.taskName }</td>
							<td>${h.endDate }</td>

						</tr>
					</c:forEach>
				</table>
				<h2>Medium Priority Task</h2>
				<table class="table table-hover">
					<tr>
						<th>Task Name</th>
						<th>Task Deadline</th>

					</tr>
					<c:forEach items="${medoum}" var="m">
						<tr>
							<td>${m.taskName }</td>
							<td>${m.endDate }</td>

						</tr>
					</c:forEach>
				</table>
				<h2>Low Priority Task</h2>
				<table class="table table-hover">
					<tr>
						<th>Task Name</th>
						<th>Task Deadline</th>

					</tr>
					<c:forEach items="${low}" var="l">
						<tr>
							<td>${l.taskName }</td>
							<td>${l.endDate }</td>

						</tr>
					</c:forEach>
				</table>
			</security:authorize>
		</div>
		<jsp:include page="/WEB-INF/jsp/footer.jsp" />
	</div>
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
