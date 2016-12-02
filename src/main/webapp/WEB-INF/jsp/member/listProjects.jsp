<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="jumbotron">
	<%-- <h2>Projects you are working on</h2>

	<table class="table table-hover">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Desciption</th>

			<th>Created By</th>


			<th>Project Type</th>
			<th>Operation</th>
		</tr>
		<c:forEach items="${projects}" var="project">
			<tr>
				<td>${project.id }</td>
				<td>${project.projectName }</td>
				<td>${project.projectDescription }</td>

				<td>${project.createdUser.userName }</td>


				<td>${project.projectType.projectType }</td>
				<td><a href="viewProject.html?id=${project.id}">View</a></td>
			</tr>
		</c:forEach>
	</table> --%>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<i class="fa fa-clock-o fa-fw"></i> Projects Panel
			</h3>
		</div>
		<div class="panel-body">
			<div class="list-group">
				<c:forEach items="${projects}" var="project">
					<a href="viewProject.html?id=${project.id}" class="list-group-item">
						<span class="badge">${project.projectType.projectType }</span> <i
						class="fa fa-fw fa-calendar"></i> ${project.projectName }
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


