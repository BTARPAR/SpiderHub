<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<div class="jumbotron">
	<div class="row text-center pad-top ">
		<div class="col-md-12">
			<h2 style="color: black;">Assign Task to User</h2>
		</div>
	</div>
	<div class="row  pad-top">
		<div
			class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
			<div class="panel panel-success">
				<div class="panel-heading">
					<strong> Users </strong>
				</div>
				<div class="panel-body">
					<!-- This is a SPRING_MVC Form -->
					<form:form modelAttribute="task" role="form">

						<p style="color: red; text-align: center;">${sessionScope.error}</p>
						<h4 class="text-info">Task Name:</h4>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"></div>
								<form:input path="id" type="hidden" value="${task.id }" />
								<form:input path="taskDescription" type="hidden"
									value="${task.taskDescription}" />
								<form:input path="projectTasks" type="hidden"
									value="${task.projectTasks }" />
								<form:input path="taskName" type="hidden"
									value="${task.taskName }" />
								${task.taskName }
							</div>
						</div>
						<h4 class="text-info">Assign User For Task</h4>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"></div>
								<table class="table table-hover">
									<tr>
										<th></th>
										<th>Users</th>
									</tr>
									<c:forEach items="${users}" var="user">
										<tr>

											<td><input type="radio" name="user" value="${user.id}" /></td>
											<td>${user.userName}</td>

										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
						<h4 class="text-info">End Date</h4>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"></div>
								<input type="date" name="endDate" placeholder="YYYY/mm/dd"
									class="form-control" required="true" />
							</div>
						</div>
						<h4 class="text-info">End Time</h4>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"></div>
								<input type="time" name="endTime" class="form-control"
									required="true" />
							</div>
						</div>
						<h4 class="text-info">Select Priority</h4>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"></div>
								<select name="priority">
									<c:forEach items="${priority}" var="tp">
										<option value="${tp.id}">${tp.priorityType}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<input class="btn btn-primary" type="submit" name="action"
							value="Assign">
						<hr />
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
