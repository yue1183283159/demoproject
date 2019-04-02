<%@ page pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>500 error page</title>
</head>
<body>
	<h2>Server occured error.</h2>
	<div>
		<%=exception.getMessage() %>
	</div>
</body>
</html>