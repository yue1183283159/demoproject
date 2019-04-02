<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JSTL Demo</h2>
	<p>
		<c:set var="staffID" value="1001" scope="session"></c:set>
		<c:out value="${staffID }" default="<h2>0</h2>" escapeXml="false"></c:out>
	</p>
	<p>
		<c:if test="true">
			<h2>c if 单条件判断</h2>
		</c:if>
	</p>
	<p>
		c choose多条件判断
		<c:choose>
			<c:when test="true">
				<button>Test</button>
			</c:when>
			<c:otherwise>
				<button>No Test</button>
			</c:otherwise>
		</c:choose>
	</p>

	<p>
	c forEach循环输出
		<%
			List<String> strList = new ArrayList<String>();
			strList.add("sa01");
			strList.add("sa02");
			strList.add("sa03");
			pageContext.setAttribute("strs", strList);
		%>
		<c:forEach items="${strs }" var="str" varStatus="varSta">
			<div>编号：${varSta.count }&nbsp;&nbsp;&nbsp;值：${str }</div>
		</c:forEach>

	</p>

</body>
</html>