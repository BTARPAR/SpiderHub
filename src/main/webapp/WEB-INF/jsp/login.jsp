<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>SpiderHub Login</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/loginStyle.css" />
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/hr.css" />
</head>
<body background="<%=request.getContextPath()%>/images/bg3.jpg">
	<div id="loginModal" class="modal show" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="border-bottom: none;">
					<div class="text-center">
						<h2>SpiderHub</h2>
					</div>
					<h3 class="text-center">Login</h3>
				</div>
				<hr class="colorgraph">
					

					<div class="modal-body">
						<form action="login" method="post">
							<p style="color: red; text-align: center;">${sessionScope.error}</p>
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-envelope"></i></span> <label
										for="username">Username: </label> <input type="text"
										name="username" id="username" required=required/>
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-lock"></i></span> <label for="password">Password:</label>
									<input type="password" name="password" id="password" required=required/>
								</div>
							</div>
							<div class="form-group">
								<input class="btn btn-primary center-block" type="submit"
									name="login" value="Sign in">
							</div>
							<div class="form-group pull-right">
								<a href="userRegistration.html" class="btn btn-success btn-md">Register</a>
								<input class="btn btn-danger btn-md" type="reset" value="Reset" />
							</div>
						</form>
						<c:if test="${param.error !=null }">
						<p>Please Try Again.</p>
						</c:if>
					</div>
					<div class="modal-footer">
						<div class="col-md-12">
							<p>&copy; SpiderHub</p>
						</div>
					</div>
			</div>
		</div>
	</div>
</body>
</html>
