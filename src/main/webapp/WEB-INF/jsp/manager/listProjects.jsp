<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

		<div class="jumbotron">
		<h2>Project Management</h2>

		<table class="table table-hover">
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Desciption</th>
				<th>Created Date</th>
				<th>Created By</th>
				<th>Github Link</th>
				<th>Status</th>
				<th>Project Type</th>
				<th>Opertations</th>
				<th>Report Generation</th>
			</tr>
			<c:forEach items="${projects}" var="project">
				<tr>
					<td>${project.id }</td>
					<td>${project.projectName }</td>
					<td>${project.projectDescription }</td>
					<td>${project.createdDate }</td>
					<td>${project.createdUser.userName }</td>
					<td>${project.projectGitHubLink }</td>
					<td><c:if test="${not project.delete }">
							<a href="disable.html?id=${project.id }"><img
								src="<%=request.getContextPath()%>/IMAGE/delete.png" /></a>
						</c:if> <c:if test="${project.delete }">
					Done
					</c:if></td>
					<td>${project.projectType.projectType }</td>
					<td><c:if test="${not project.delete }">
							<a href="viewProject.html?id=${project.id}">View</a> | <a
								href="editProject.html?id=${project.id }">Edit</a>
						</c:if> <c:if test="${project.delete }">
					Done
					</c:if></td>
					<td><a href="report.html?id=${project.id }">Report</a></td>
				</tr>
			</c:forEach>
		</table>
		<p>
			<a href="addProject.html">Add new project.</a>
		</p>
		</div>
		
