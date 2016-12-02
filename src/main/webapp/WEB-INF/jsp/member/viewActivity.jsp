<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
	<form:form modeAttribute="taskActivity">

		<table class="table table-hover">
			<tr>
				<th>Activity id</th>
				<th>Activity complete</th>
				<th>Activity start Time</th>
				<th>activity endtime</th>
				<th>Task Id</th>
				<th>userName</th>
			</tr>
			<c:forEach items="${activityModel}" var="activity">
				<tr>
					<td>${activity.id}</td>
					<td>${activity.complete}</td>
					<td>${activity.startTime}</td>
					<td>${activity.endTime}</td>
					<td>${activity.activityOfTask.id}</td>
					<td>${activity.activityOfTaskByUser.userName}</td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${empty activityId}">
			<input class="btn btn-primary" type="submit" name="action"
				value="start" />
			<input type="hidden" name="activityId" value="${activityId}"></input>
		</c:if>
		<c:if test="${not empty activityId}">
			<input type="hidden" name="activityId" value="${activityId}"></input>

			<input class="btn btn-primary" type="submit" name="action"
				value="stop"> </input>
		</c:if>
	</form:form>
</div>