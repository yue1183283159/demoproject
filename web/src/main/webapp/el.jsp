<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EL Demo</title>
</head>
<body>
	${5+4 }
	<br>
	<p>${sessionScope["user"] }</p>
	<p>使用EL的param对象获取提交的参数：${param["name"] }</p>
</body>
</html>