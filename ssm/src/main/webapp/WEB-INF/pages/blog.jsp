<%@ page pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
	pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSM demo, blog 增删该查案例</title>
<script src="${basePath }/static/jquery-3.1.1.min.js"></script>
<style>
td {
	padding: 10px 10px;
}
</style>
</head>
<body>
	<div style="margin-bottom: 20px; padding-top: 20px; padding-left: 20px">
		<button type="button" id="btnSave">插入一条Blog</button>
		<button type="button" id="btnSearchAll">查询所有的Blog</button>
		<button type="button" id="btnMultiDelete">批量删除多条Blog</button>
		<button type="button" id="btnCrawl">爬取博客园上的博客</button>
	</div>

	<div id="info"></div>
	<table id="blogtb" border="1">
		<thead>
			<tr>
				<th style="width: 5%">编号</th>
				<th style="width: 35%">标题</th>
				<th style="width: 45%">简介</th>
				<th style="width: 10%">作者</th>
				<th style="width: 5%">阅读人数</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5">没有数据</td>
			</tr>
		</tbody>
	</table>

	<script>
		$(function() {
			$("#btnSave").click(function() {
				$.ajax({
					type : "post",
					url : '${basePath}/blog/saveblog',
					data:{id:1,title:'test',brief:'test data'},
					success : function(data) {
						alert(data);
					}
				});

			});

			$('#btnMultiDelete').click(function(){
				
				$.ajax({
					type : "post",
					url : '${basePath}/blog/detete',
					data : {ids:'1,2,3,4,5,6,7,8'},
					success : function(data) {
						alert(data);
					}
				});
			});
			
			$("#btnSearchAll").click(
					function() {
						$.ajax({
							type : "get",
							url : '${basePath}/blog/getallblogs?rnd=' + Math.random(),
							type : 'json',
							success : function(data) {
								var len = data.length;
								var html = '';
								for (var i = 0; i < len; i++) {
									var blog = data[i];
									html += '<tr><td>' + blog.id + '</td><td><a href="'+blog.href+'" target="_blank">' + blog.title + '</a></td><td>' + blog.brief + '</td><td>'
											+ blog.author + '</td><td>' + blog.readCount + '</td></tr>';
								}

								$('#info').html('爬取'+len+'份博客');
								$('#blogtb>tbody').html(html);

							}
						});

					});

			$('#btnCrawl').click(function() {
				$.ajax({
					type : 'post',
					url : '${basePath}/blog/crawlblog',
					success : function(data) {
						alert('已经开始爬取博客了');
					}

				});

			});

		})
	</script>
</body>
</html>