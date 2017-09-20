<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "";
%>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
<title>mongo首页</title>
<link rel="stylesheet" href="<%=basePath%>/static/layui/css/layui.css"
	media="all">
</head>
<body>
	<div class="layui-layout layui-layout-admin">
		<%@include file="../common/top.jsp" %>
		<div class="layui-body">
			<div style="padding: 15px;">
			<!-- 内容主体区域 -->
			<div class="layui-carousel" id="test10">
			  <div carousel-item="">
			    <div><img src="<%=basePath%>/static/images/6ec95136a0f7d316a1405d60bc713f3c.jpg" width="1500px" height="360px"></div>
			    <div><img src="<%=basePath%>/static/images/47900c7421fa7647083b091a1f92c74d.png" width="1500px" height="360px"></div>
			    <div><img src="<%=basePath%>/static/images/86d69f3aecf20362d8d1f5a39a67e5e1.jpg" width="1500px" height="360px"></div>
			  </div>
			</div>
			</div>
		</div>
		<%@include file="../common/bottom.jsp" %>
	</div>
	<script src="<%=basePath%>/static/js/jquery/jquery-1.12.3.min.js"></script>
	<script src="<%=basePath%>/static/layui/layui.all.js"></script>
	<script>
		layui.use(['carousel', 'form'], function(){
		  var carousel = layui.carousel
		  ,form = layui.form;
		  
		  //常规轮播
		  carousel.render({
		    elem: '#test1'
		    ,arrow: 'always'
		  });
		  
		  //改变下时间间隔、动画类型、高度
		  carousel.render({
		    elem: '#test2'
		    ,interval: 1800
		    ,anim: 'fade'
		    ,height: '120px'
		  });
		  
		  //设定各种参数
		  var ins3 = carousel.render({
		    elem: '#test3'
		  });
		  //图片轮播
		  carousel.render({
		    elem: '#test10'
		    ,width: '1500px'
		    ,height: '360px'
		    ,interval: 5000
		  });
		  
		  //事件
		  carousel.on('change(test4)', function(res){
		    console.log(res)
		  });
		  
		  var $ = layui.$, active = {
		    set: function(othis){
		      var THIS = 'layui-bg-normal'
		      ,key = othis.data('key')
		      ,options = {};
		      
		      othis.css('background-color', '#5FB878').siblings().removeAttr('style'); 
		      options[key] = othis.data('value');
		      ins3.reload(options);
		    }
		  };
		  
		  //监听开关
		  form.on('switch(autoplay)', function(){
		    ins3.reload({
		      autoplay: this.checked
		    });
		  });
		  
		  $('.demoSet').on('keyup', function(){
		    var value = this.value
		    ,options = {};
		    if(!/^\d+$/.test(value)) return;
		    
		    options[this.name] = value;
		    ins3.reload(options);
		  });
		  
		  //其它示例
		  $('.demoTest .layui-btn').on('click', function(){
		    var othis = $(this), type = othis.data('type');
		    active[type] ? active[type].call(this, othis) : '';
		  });
		});
	</script>
</body>
</html>
