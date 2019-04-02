<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
	pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring MVC Demo</title>
<script src="${basePath }/static/jquery-3.1.1.min.js"></script>
</head>
<body>
	<h2>page:${pageScope.message }</h2>
	<h2>request:${requestScope.message }</h2>
	<h2>session:${sessionScope.message }</h2>
	<h2>application:${applicationScope.message }</h2>
	<h2>${name }</h2>
	<a href="${basePath }/Test/doSayHello?name=sa">测试传参数</a>
	<a href="${basePath }/Test/deleteItems?ids=1,3,4">自定装换数组</a>
	<h2>RESTFul Demo</h2>
	<a href="${basePath }/Rest/data/12">GET DATA</a>
	<button type="button" id="btnTest1">Test Json To Bean</button>
	<h2>Netty WebSocket Demo</h2>
	
	<div>
	<input type="text" id="txtMsg"/>
	<input type="button" value="Send" id="btnSend"/>
	</div>
	
	
	<script>
	var socket;
	if(!window.WebSocket){
		window.WebSocket=window.MozWebSocket;	
	}
	
	if(window.WebSocket){
		socket=new WebSocket("ws://localhost:8092/websocket");
		socket.onmessage=function(event){
			alert(event.data);
		};
		socket.onopen=function(event){
			
		};
		socket.onclose=function(event){
			
		};
	}
	
	function sendMsg(message){
		if(!window.WebSocket){
			return;
		}
		if(socket.readyState==WebSocket.OPEN){
			socket.send(message);
		}else{
			alert("websocket连接建立失败");
		}
	}
	
	
		$(function() {
			$('#btnTest1').click(function() {
				$.ajax({
					type : 'post',
					url : '${basePath}/Rest/add.do',
					contentType : 'application/json',
					data : JSON.stringify({
						id : 12,
						name : 'administrator',
						age : 23
					}),
					success : function(ret) {
						alert(ret);
					}
				});

			});

		})
	</script>
</body>
</html>