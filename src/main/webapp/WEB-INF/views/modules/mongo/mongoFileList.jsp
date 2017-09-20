<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta name="decorator" content="default"/>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/common/hdfsmain.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/common/hdfsload.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/common/hdfsShow.js"></script>
<title>文件列表</title>
</head>
<body>
   	<div class="demoTable">
	  文件夹名称：
	  <div class="layui-inline">
	    <input class="layui-input" name="inputsearch" id="inputsearch" lay-verify="inputsearch" autocomplete="off">
	  </div>
	  文件名称：
	  <div class="layui-inline">
	    <input class="layui-input" name="filesearch" id=filesearch lay-verify="filesearch" autocomplete="off">
	  </div>
	  
	  <button class="layui-btn" data-type="reload" onclick="searchFile();">搜索</button>
	</div>
    <table id="mongolist" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-danger layui-btn-small" lay-event="download">下载</a>
		<a class="layui-btn layui-btn-small layui-btn-normal" lay-event="del">删除</a>
	</script>
	
	<div id="maskstr" class="maskstr" style="display: none;">
		<div class="inner">
			<div class="load-container load4">
				<div class="loader">
					<font color="#000" style="font-weight: bold;" size="3">删除中</font>
				</div>
			</div>
		</div>
	</div>
<script>
var tableIns;
layui.use(['table','element'], function(){
	$("#wjzl").addClass("layui-nav-itemed");
	$("#mongoFileList").addClass("layui-this");
  	var table = layui.table;
  	var layer = layui.layer;
	//执行渲染
	tableIns = table.render({
		elem: '#mongolist' //指定原始表格元素选择器（推荐id选择器）
		//,skin: 'line' //行边框风格
	    ,even: true //开启隔行背景
		,url: 'mongoFile/mongoFileList'
		,cols: [[
			//{checkbox: true}
			{field: 'objectid', width:220,sort: true,title: 'ObjectId'} //其它参数在此省略
			,{field: 'modelId', width:150,sort: true,title: '文件夹名称'} //其它参数在此省略
		    ,{field: 'id', width:320,title: '编号'} //其它参数在此省略
		    ,{field: 'filename', width:380, sort: true,title: '文件名称',style:'color: blue;'}
		    ,{field: 'contentType', width:100,title: '文件类型'}
		    ,{field: 'length',width:180,sort: true,title: '文件大小'}
		    ,{field: 'uploadSystemDate',width:180,sort: true,title: '上传时间'}
		    ,{fixed: 'right',title: '操作', width:150, align:'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
		    ]] //设置表头
		,limits: [10,20]
		,limit:10
		,page:true
	})
	
	//监听工具条
	table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		  var data = obj.data; //获得当前行数据
		  var layEvent = obj.event; //获得 lay-event 对应的值
		  var tr = obj.tr; //获得当前行 tr 的DOM对象
		 
		  if(layEvent === 'download'){ //下载
		    //do somehing
		    layer.confirm('下载的文件将默认保存在"D://mongofiles"', function(index){
		      //alert(data.id);
		      var reid = data.id;
		      downloadFile(reid);
		      //obj.del(); //删除对应行（tr）的DOM结构
		      //layer.close(index);
		      //向服务端发送删除指令
		    });
		  }
		  if(layEvent === 'del'){ //删除
		    layer.confirm('确定删除此文件吗？', function(index){
		      //alert(data.id);
		      var reid = data.objectid;
		      deleteFile(reid);
		      //obj.del(); //删除对应行（tr）的DOM结构
		      layer.close(index);
		      //向服务端发送删除指令
		    });
		  }
	});
});

function downloadFile(reid){
	var layer = layui.layer;
	$.ajax({
        cache: true,
        type: "POST",
        url:"mongoFile/downloadFile",
        data:{reid:reid},
        error: function(request) {
        	layer.msg("下载失败！");
        },
        success: function(data) {
        	if(data.code == 1){
        		layer.msg("下载成功！");
        	}else{
        		layer.msg("下载失败！");
        	}
        }
    });
}


function deleteFile(reid){
	showMask();
	var layer = layui.layer;
	$.ajax({
        cache: true,
        type: "POST",
        url:"mongoFile/deleteFile",
        data:{reid:reid},
        error: function(request) {
        	layer.open({
   			  title: '提示信息'
   			  ,content: '删除失败！',
   			  end: function () {
                  location.reload();
              }
   			}); 
        	hideMask();
        },
        success: function(data) {
        	if(data.code == 0){
        		layer.open({
       			  title: '提示信息'
       			  ,content: '删除成功！',
       			  end: function () {
                      location.reload();
                  }
       			});  
        	}else{
        		layer.open({
       			  title: '提示信息'
       			  ,content: '删除失败！',
       			  end: function () {
                      location.reload();
                  }
       			}); 
        	}
        	hideMask();
        }
    });
}

function searchFile(){
	tableIns.reload({
        //设定异步数据接口的额外参数
        where: {
            name: document.getElementById("inputsearch").value
          , filesearch: document.getElementById("filesearch").value
        }
    });
}
</script>

</body>
</html>
