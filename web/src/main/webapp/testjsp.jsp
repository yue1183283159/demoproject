<%@page import="java.util.*"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Jsp Demo</title>
</head>
<body>
	<%@include file="_header.jsp"%>

	<%=request.getContextPath()%><br>
	<%="Hello JSP."%>
	<br>
	<%
		for (int i = 0; i < 5; i++) {
			out.write("\r\n");
			out.write("output:" + i);
			out.write("\t");
		}
	%>
	<br>
	<%=this.getClass()%>
	<br>
	<%=this.getServletContext()%>
	<br>
	<%
		pageContext.setAttribute("p_name", "Test Jsp Page");
	%>
	<%=pageContext.getAttribute("p_name")%>
	<br>
	<%
		out.write(pageContext.getAttribute("p_name").toString());
	%>
	<br>
	<%=request.getLocalAddr()%>

	<h2>EL表达式</h2>
	<%--
	在EL表达式中书写一个变量的值，是从四大域中查找，按照域的范围，从小到大的顺序查找。
	找到就返回，找不到就什么也不输出。
	
	 --%>
	<% String testStr="Test EL"; %>
	${testStr }<%--不能输出，因为testStr不在四大域中 --%>
	<br>
	${p_name }
	<br> ${123+321 }

	<br>
	<%
		String[] teachers = { "陈子枢", "王海涛", "花倩" };
		request.setAttribute("teachers", teachers);
	%>
	${teachers[0] }, ${teachers[1] }, ${teachers[2] }
	<br>
	<%
		Map<String, String> actorMap = new HashMap();
		actorMap.put("name", "林青霞");
		actorMap.put("age", "24");
		request.setAttribute("actor", actorMap);
	%>
	${actor.name },${actor.age}
	<br>
	<h2>JSTL 标签库</h2>
	<h3><%="c:set demo" %></h3>
	<c:set var="name" value="Lily" scope="page"></c:set>
	<%--
		scope属性用于指定将属性存入哪一个域中，值为：
		page:pageContext域
		request:request域
		session:session域
		application:ServletContext域
	 --%>
	${name }
	<br>
	<% Map map=new HashMap();
		map.put("name","Lucy");
		map.put("country","US");
		request.setAttribute("map", map);
	%>
	${map.name },${map.country }
	<br>
	<c:set target="${map }" property="name" value="Lily"></c:set>
	<c:set target="${map }" property="country" value="China"></c:set>
	${map.name },${map.country }
	
	<h3><%="c:if demo" %></h3>
	<c:if test="${3>5 }">Yes</c:if>,
	<c:if test="${!(3>5) }">No</c:if>
	
	<h3><%="c:forEach Demo" %></h3> 
	<c:forEach items="${teachers }" var="name" varStatus="status">
		${name }<c:if test="${!status.last }">,</c:if>
	</c:forEach>
	
	<br>
	<c:forEach items="${map }" var="entry">
		${entry.key }:${entry.value }
	</c:forEach>
	<br>
	<c:forEach begin="1" end="100" step="2" var="i" varStatus="status">
		${i }<c:if test="${!status.last }">,</c:if>
	</c:forEach>
	<%--
	varStatus="status" 返回一个表示循环状态的对象，该对象具有以下属性：
	（1） count:表示当前遍历的元素是第几个
	（2）first:表示当前遍历的元素是否为第一个
	（3）last:表示当前遍历的元素是否为最后一个
	 --%>
	
	<br>
	<c:forEach begin="1" end="100" step="2" var="i" varStatus="status">
		<c:if test="${status.count%3==0 }">
			<span style="color:red">${i }</span>
		</c:if>
		<c:if test="${status.count%3!=0 }">
			${i }
		</c:if>
		<c:if test="${!status.last }">,</c:if>
	</c:forEach>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<%@include file="_footer.jsp"%>
</body>
</html>