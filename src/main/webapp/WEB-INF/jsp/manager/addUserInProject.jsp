<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<!-- <link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 -->
<!-- <link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 -->

<!-- <script>
	$(function() {
		var availableTags =  ${users} ;
		$("#tags").autocomplete({
			source : $(this)
		});
	});
</script>
 -->
<!-- 
<script>
	$(document).ready(function() {
		$('#tags').autocomplete({
			serviceUrl : '${pageContext.request.contextPath}/getTags',
			paramName : "tagName",
			delimiter : ",",
			transformResult : function(response) {
				return {
					//must convert json to javascript object before process
					suggestions : $.map($.parseJSON(response), function(item) {
						return {
							value : item.tagName,
							data : item.id
						};
					})
				};
			}
		});
	});
</script>
 -->

<spring:url value="/manager/addUserInProject.html" var="formUrl" />
<spring:url value="/getTags.json" var="formJsonUrl" />

<script>
	$(function() {
		$("#tags")
				.autocomplete(
						{
							source : function(request, response) {
								/* $.getJSON("${formJsonUrl}", {
									term : request.term
								}, response); */
								$
										.ajax({
											url : "${formJsonUrl}",
											data : {
												term : request.term,
											},
											dataType : 'json',
											success : function(data) {
												//json here
												response(data);
												$('#list').empty();
												$
														.each(
																data,
																function(index) {
																	$("#list")
																			.append(
																					'<tr><td><input type="checkbox" id="chk" name="chksms"' +
									'	value= "' + data[index] + '"/> '
																							+ data[index]
																							+ ' </td></tr>');
																});
											}
										});
							}
						});
	});
	/* $('#dealerName').autocomplete(
			{
				source : function(request, response) {
					$.getJSON("/example/location/example.json?term="
							+ request.term, function(data) {
						response($.map(data.dealers, function(value, key) {
							return {
								label : value,
								value : key
							};
						}));
					});
				},
				minLength : 2,
				delay : 100
			}); */
	/* 	$.ajax({
	 type : "GET",
	 url : "${formJsonUrl}",
	 success : function(response) {
	 $("#tags").autocomplete({
	 source : response
	 });
	 }
	 }); */
</script>

<div class="jumbotron">
	<div class="row text-center pad-top ">
		<div class="col-md-12">
			<h2 style="color: black;">Add User In Project</h2>
		</div>
	</div>
	<div class="row  pad-top">
		<div
			class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
			<div class="panel panel-success">
				<div class="panel-heading">
					<strong> Users </strong>
				</div>
				<div>
				<div class="panel-body">
					<!-- This is a SPRING_MVC Form -->
					<form:form modelAttribute="project" role="form">

						<p style="color: red; text-align: center;">${sessionScope.error}</p>

						<div class="ui-widget">
							<label for="tags" class="text-info">Users: </label> <input
								id="tags">
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"></div>


								<table class="table table-hover">
								<br />
								<br />
									<div id="list"></div>
									<%-- <c:forEach items="${users}" var="user">
										<tr>
											<td><input type="checkbox" id="chk" name="chksms"
												value="${user.id}" /></td>
											<td>${user.userName}</td>
										</tr>
									</c:forEach> --%>

								</table>
							</div>
						</div>


						<input class="btn btn-primary" type="submit" name="add"
							value="Add">
						<hr />


					</form:form>
				</div>
			</div>
		</div>
	</div>
