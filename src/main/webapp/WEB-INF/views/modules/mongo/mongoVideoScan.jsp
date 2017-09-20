<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/modules/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta name="decorator" content="default"/>
<title>视频观看</title>
</head>
<body>
    <blockquote class="layui-elem-quote"><button class="layui-btn layui-btn-normal" id="fhbtn">返回列表页面</button></blockquote>
    <c:choose>
	   <c:when test="${not empty message}"> 
	         <video src="<%=request.getContextPath()%>/cache/${message}" controls="controls" autoplay="autoplay">
	    		您的浏览器不支持 video 标签。
	    	 </video>   
	   </c:when>
	</c:choose>

<script>
layui.use(['table','element'], function(){
	$("#mongoVideoList").addClass("layui-this");
	$("#fhbtn").click(function() {
		window.location.href="mongoVideoList";
  	})
});
</script>

</body>
</html>
