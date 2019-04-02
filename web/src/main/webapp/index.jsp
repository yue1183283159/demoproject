<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
	<h2>Hello JSP</h2>
	<h2>Test Servlet Request GetParameter Function</h2>
	<h1>GET提交</h1>
		<form action="/JavaWeb/TestRequest" method="GET">
			用户名: <input type="text" name="username" />
			昵称: <input type="text" name="nickname" />
			<input type="submit" value="提交" />
		</form>
		<h1>POST提交</h1>
		<form action="/JavaWeb/TestRequest" method="POST">
			用户名: <input type="text" name="username" />
			昵称: <input type="text" name="nickname" />
			爱好: <input type="checkbox" name="like" value="lanqiu" />篮球
				<input type="checkbox" name="like" value="zuqiu" />足球
				<input type="checkbox" name="like" value="taiqiu" />台球
			<input type="submit" value="提交" />
		</form>
</body>
</html>