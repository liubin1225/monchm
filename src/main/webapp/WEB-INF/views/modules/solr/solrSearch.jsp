<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>solr搜索</title>
<link rel="stylesheet" href="<%=basePath%>/static/layui/css/layui.css" media="all">
</head>
<body>
<div class="layui-layout layui-layout-admin">
  <%@include file="../common/top.jsp" %>
  <div class="layui-body">
	<div class="container" style="margin-top: 50px">
  	<div class="row">
  		<div class="col-md-6 col-md-offset-3">
  			<img src="<%=basePath%>/static/images/pic.jpg" />
  		</div>
  		<div class="col-md-10">
  			<input type="text" class="form-control" id="info" placeholder="请输入搜索内容">
  		</div>
  		<div class="col-md-2">
  		<button id="search" type="button" class="layui-btn layui-btn-normal">搜索</button>
  		</div>
  	</div>
  	</div>
  </div>
  <%@include file="../common/bottom.jsp" %>

</div>
<script src="<%=basePath%>/static/js/jquery/jquery-1.12.3.min.js"></script>
<script src="<%=basePath%>/static/layui/layui.js"></script>
<script>
		layui.use(['table','element'], function(){
			$("#solrSearch").addClass("layui-this");
		});
</script>
</body>
</html>