<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://aiyunnet.com.cn" prefix="aiyun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Custom Tag Demo</title>
</head>
<body>
	<h2>use custom tag</h2>
	<p>
		自定义label标签内容，传入caption id从数据库得到caption
		<aiyun:yunLabel captionId="1"></aiyun:yunLabel>
	</p>
	<p>
		自定义标签的生命周期
		<aiyun:tagLife></aiyun:tagLife>
	</p>
	<p>
		<aiyun:attrTag num="5" upper="true">
			<h3>带属性的标签 aaaa</h3>
		</aiyun:attrTag>
	</p>
	<p>
		<aiyun:ifTag test="${10>8 }">
			<h2>自定义if标签</h2>
		</aiyun:ifTag>
	</p>
	<p>
		<aiyun:chooseTag>
			<aiyun:whenTag test="true">
				<h3>ChooseWhenTag -- When</h3>
			</aiyun:whenTag>
			<aiyun:otherwise>
				<h3>ChooseWhenTag -- Otherwise</h3>
			</aiyun:otherwise>
		</aiyun:chooseTag>
	</p>
	<p>
	<h4>自定义foreach标签</h4>
	<%
		List<String> strList = new ArrayList<String>();
		strList.add("sa01");
		strList.add("sa02");
		strList.add("sa03");
		pageContext.setAttribute("strs", strList);
	%>
	<aiyun:forEachTag items="${strs }" var="str">
		<h4>${str }</h4>
	</aiyun:forEachTag>
	</p>
</body>
</html>