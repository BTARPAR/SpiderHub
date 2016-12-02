<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="header clearfix">
	<nav>
		<ul class="nav nav-pills pull-right">
			<li role="presentation" class="active"><a href="index.html">Home</a></li>

			<li role="presentation"><security:authorize access="anonymous">
					<a href="<c:url value='/login.html' />">Login</a>
				</security:authorize></li>


			<security:authorize access="authenticated">
				<security:authorize access="hasRole('ADMIN')">

					<li role="presentation"><a href="admin/listProjects.html">Project
							Management</a></li>
					<li role="presentation"><a href="admin/userManagement.html">User
							Management</a></li>
				</security:authorize>
				<security:authorize access="hasAnyRole('MANAGER')">

					<li role="presentation"><a href="manager/listProjects.html">Project
							Management</a></li>


				</security:authorize>
				<security:authorize access="hasRole('MEMBER')">
					<li role="presentation"><a href="member/listProjects.html">Projects
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
</div>