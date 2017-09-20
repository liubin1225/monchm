<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta name="decorator" content="default"/>
<title>系统日志</title>
</head>
<body>
    <table id="scanlogt"></table>
    <input id="reid" type="hidden"/>

<script>
$(function(){
	$("#scanlogtop").addClass("layui-this");
	//var reid = $("#reid").val();
	//monlist(reid);
	monlist()
})
function monlist(){
	layui.use(['table','element'], function(){
	  	var table = layui.table;
		//执行渲染
		table.render({
			elem: '#scanlogt' //指定原始表格元素选择器（推荐id选择器）
			//,skin: 'line' //行边框风格
		    ,even: true //开启隔行背景
			,url: 'mongoFile/scanLog'
			,cols: [[
				{field: 'op', width:180,title: '操作名称'},
				{field: 'msg', width:280,title: '操作状态'},
				{field: 'uploadSystemDate', width:180, sort: true,title: '操作时间'},
				{field: 'ip', width:180, sort: true,title: '操作ip',style:'color: blue;'}
			    ]] //设置表头
			,limits: [15,20,30,50]
			,limit:15
			,page:true
			,reid:reid
			,done: function(data){
				$("#reid").val(data.msg);
			}
		})
	});
}
</script>
</body>
</html>
