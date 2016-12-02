<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
	<table class="table table-hover">
		<tr>
			<th>ID</th>
			<td>${task.id}</td>
		</tr>
		<tr>
			<th>TaskName</th>
			<td>${task.taskName}</td>
		</tr>
		<tr>
			<th>Description</th>
			<td>${task.taskDescription}</td>
		</tr>

	</table>
	<table class="table table-hover">
		<tr>
			<th>File Name</th>
			<th>File Type</th>
			<th>File Upload Date</th>
			<th>File Download</th>
		</tr>
		<c:forEach items="${fileModel}" var="file">
			<tr>

				<%-- <td><input type="radio" name="files" value="${file.id}" /></td> --%>
				<td>${file.fileName}</td>
				<td>${file.fileType}</td>
				<td>${file.uploadDate}</td>
				<td><a
					href="download.html?file=${file.fileName}.${file.fileType}">Download</a></td>


			</tr>
		</c:forEach>
	</table>

	<c:if test="${not empty comments}">
		<c:forEach items="${comments}" var="c">
			<p>${c.userComment.userName}</p>
			<br />
			<p>${c.commentDesc}</p>
		</c:forEach>
	</c:if>
	<form:form modelAttribute="comment" role="form">
		<form:textarea path="commentDesc" rows="2" cols="30"
			class="form-control" required="required" />
		<input type="hidden" name="task" value="${task.id}" />
		<input class="btn btn-primary" type="submit" name="action"
			value="Comment">
	</form:form>
	<a href="assignTask.html?id=${task.id}">Assign</a>
</div>