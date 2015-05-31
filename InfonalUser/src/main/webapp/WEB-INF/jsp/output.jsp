<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.mete.infonal.util.Key"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<!doctype html>
<html lang="en">
<head>
<meta http-equiv=content-type content=text/html;charset=iso-8859-9>
<meta http-equiv=content-type content=text/html;charset=windows-1254>
<meta http-equiv=content-type content=text/html;charset=x-mac-turkish>

<script
	src="//cloud.github.com/downloads/digitalBush/jquery.maskedinput/jquery.maskedinput-1.3.min.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<link href="<c:url value="/resources/css/stylesheet.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/customscript.js" />"></script>
<script src="<c:url value="/resources/js/jquery.maskedinput.js" />"></script>


</head>
<body>

	<div id="dialog-form" title="Create new user">
		<p class="validateText">All form fields are required.</p>
		<form name="form" id="form" action="user" method="post">
			<fieldset>
				<input type="hidden" id="ident" name="ident"> <label
					for="name">Name</label> <input type="text" name="name" id="name"
					class="text ui-widget-content ui-corner-all"> <label
					for="lastName">Last Name</label> <input type="text" name="lastName"
					id="lastName" class="text ui-widget-content ui-corner-all">
				<label for="phoneNumber">Phone Number</label> <input value=""
					type="text" name="phoneNumber" id="phoneNumber"
					class="text ui-widget-content ui-corner-all">
				<!--    		captcha instance oluştur ve görüntüle  -->
				<%
					ReCaptcha captcha = ReCaptchaFactory.newReCaptcha(
							Key.RECAPTCHA_PUBLIC_KEY, Key.RECAPTCHA_PRIVATE_KEY, false);
					out.print(captcha.createRecaptchaHtml(null, null));
				%>
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px">
			</fieldset>
		</form>
	</div>

	<div id="users-contain" class="ui-widget">
		<h1>List of Users</h1>
		<table id="users" class="ui-widget ui-widget-content">
			<thead>
				<tr class="ui-widget-header ">
					<th>Name</th>
					<th>Last Name</th>
					<th>Phone Number</th>
					<th>Delete</th>
					<th>Update</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${allUsers}">
					<tr>
						<%-- 						<td>${person.id}</td> --%>
						<td>${user.name}</td>
						<td>${user.lastName}</td>
						<td>${user.phoneNumber}</td>
						<td>
							<button id="${user.id}" data-id="${user.id}"
								class="delete_button">
								<span id="${user.id}"></span>Delete
							</button>
						</td>
						<td>
							<button data-id="${user.id}" class="update_button">Update</button>
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<button id="create-user">Create</button>

	<div id="delete" title="Delete User">
		<p class="validateText">This content will be deleted. Are you
			sure?</p>
	</div>

	<div class="loading"></div>

</body>
</html>