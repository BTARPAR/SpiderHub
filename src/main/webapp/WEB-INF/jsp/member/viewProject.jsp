
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="jumbotron">
	<h2>Project Details</h2>
	<table class="table table-hover">
		<%-- <tr>
			<th>ID</th>
			<td>${project.id}</td>
		</tr> --%>
		<tr>
			<th>Project Name</th>
			<td>${project.projectName}</td>
		</tr>
		<tr>
			<th>Description</th>
			<td>${project.projectDescription}</td>
		</tr>
		<tr>
			<th>Manager</th>
			<td>${project.createdUser.userName }</td>
		</tr>
		<tr>
			<th>Project Type</th>
			<td>${project.projectType.projectType }</td>
		</tr>
	</table>
	<h1>Assigned Tasks</h1>
	<table class="table table-hover">
		<tr>
			<th>Task</th>
			<th>Task Description</th>
			<th>Operation</th>
			<th>Assigned By</th>
		</tr>

		<c:forEach items="${task}" var="task">

			<tr>
				<td>${task.taskName}</td>
				<td>${task.taskDescription}</td>
				<td><c:if test="${task.statusTasks.id==1 }">
						<a href="doneTask.html?tid=${task.id}&pid=${project.id}"><img
							src="<%=request.getContextPath()%>/IMAGE/delete.png" /></a>
					</c:if> <c:if test="${task.statusTasks.id==2 }">
					Done
					</c:if></td>
				<td>${project.createdUser.userName}</td>
				<c:if test="${not empty task.files}">
					<td><a href="viewTask.html?tid=${task.id}&pid=${project.id}">View
							Files</a></td>

				</c:if>



				<td><c:if test="${task.statusTasks.id==1 }">
						<a href="viewActivity.html?tid=${task.id}">start Activity
							Files</a>
					</c:if></td>
			</tr>


		</c:forEach>
	</table>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<i class="fa fa-clock-o fa-fw"></i> Tasks Panel
			</h3>
		</div>
		<div class="panel-body">
			<div class="list-group">
				<c:forEach items="${task}" var="task">
					<a href="viewTask.html?tid=${task.id}&pid=${project.id}"
						class="list-group-item"> <span class="badge">${task.startDate }</span><span
						class="badge"><c:if test="${task.statusTasks.id==1 }">
								On Going
							</c:if> <c:if test="${task.statusTasks.id==2 }">
					Done
					</c:if></span> <i class="fa fa-fw fa-calendar"></i> ${task.taskName}
					</a>

				</c:forEach>

				<!-- <a href="#" class="list-group-item"> <span class="badge">4
							minutes ago</span> <i class="fa fa-fw fa-comment"></i> Commented on a
						post
					</a> <a href="#" class="list-group-item"> <span class="badge">23
							minutes ago</span> <i class="fa fa-fw fa-truck"></i> Order 392 shipped
					</a> <a href="#" class="list-group-item"> <span class="badge">46
							minutes ago</span> <i class="fa fa-fw fa-money"></i> Invoice 653 has
						been paid
					</a> <a href="#" class="list-group-item"> <span class="badge">1
							hour ago</span> <i class="fa fa-fw fa-user"></i> A new user has been
						added
					</a> <a href="#" class="list-group-item"> <span class="badge">2
							hours ago</span> <i class="fa fa-fw fa-check"></i> Completed task: "pick
						up dry cleaning"
					</a> <a href="#" class="list-group-item"> <span class="badge">yesterday</span>
						<i class="fa fa-fw fa-globe"></i> Saved the world
					</a> <a href="#" class="list-group-item"> <span class="badge">two
							days ago</span> <i class="fa fa-fw fa-check"></i> Completed task: "fix
						error on sales page"
					</a> -->
			</div>

		</div>
	</div>
</div>