<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <title>库名列表</title>
  <meta name="renderer" content="webkit">
  <meta name="decorator" content="default"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/global.css" media="all">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/table.css" media="all">
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
            
    <ul id="menuTree"></ul>
    <table id="mongolist" lay-filter="test"></table>
 
<script>
var pid;
var tableIns;
var table;
layui.config({
	base: '${pageContext.request.contextPath}/static/js/'
}).use(['tree','ajax','table','element','paging'], function(){
		 tree = layui.tree;
		 ajax = layui.ajax;
		 table = layui.table;
		 paging = layui.paging();
		 refresh();
});
function refresh(){
	$("#mongoTreeList").addClass("layui-this");
	initTree();
	//getPageData("");
}
//初始化左侧菜单树
function initTree(){
	$("#menuTree").html("")
	var d = ajax.ajaxFunc("${pageContext.request.contextPath}/mongoFile/scanDatabases",null,"json",null);
	layui.tree({
	    elem: '#menuTree' //传入元素选择器
	    ,nodes: d.data
	    ,click: function(node){
	    	getPageData(node.id);
	    }  
	});
}

//function getPageData(pid){
	//执行渲染
	//tableIns = table.render({
	//	elem: '#mongolist' //指定原始表格元素选择器（推荐id选择器）
		//,skin: 'line' //行边框风格
	//    ,even: true //开启隔行背景
	//	,url: 'mongo/mongodbTreeList'
	//	,cols: [[
	//		{checkbox: true}
	//	    ,{field: 'id', width:280,title: '编号'} //其它参数在此省略
	//	    ,{field: 'filename', width:380, sort: true,title: '文件名称',style:'color: blue;'}
	//	    ,{field: 'contentType', width:100,title: '文件类型'}
	//	    ,{field: 'length',width:180,sort: true,title: '文件大小'}
	//	    ,{field: 'uploadSystemDate',width:180,sort: true,title: '上传时间'}
	//	    ]] //设置表头
	//	,limits: [10,20]
	//	,limit:10
	//	,page:true
	//	,pid:pid
	//	,id: 'testReload'
	//})
//}

function getPageData(pid){
	paging.init({
		url: "${pageContext.request.contextPath}/mongo/mongodbTreeList",
		elem: '#mongolist',
		//tempElem: '#conTemp',  
		pageConfig: {        
			//elem: '#pages',  
			pageSize: 10,     
			pid:pid
		}
	});
}

</script>

</body>
</html>