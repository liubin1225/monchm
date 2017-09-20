<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="default"/>
<title>文件上传</title>
</head>
<body>
	<blockquote class="layui-elem-quote">文件拖拽上传</blockquote>
	<hr class="layui-bg-red">
 	<div class="layui-upload-drag" id="test10">
	  <i class="layui-icon"></i>
	  <p>点击上传，或将文件拖拽到此处</p>
	</div>
<script>
$(function(){
	$("#wjzl").addClass("layui-nav-itemed");
	$("#mongoFileDrag").addClass("layui-this layui-nav-tree layui-nav-side");
	monupload();
})
function monupload(){
	layui.use(['layer','upload','element'], function(){
		  var upload = layui.upload;
		  //多文件列表示例
		  var demoListView = $('#demoList')
		  ,uploadListIns = upload.render({
		    elem: '#test10'
		    ,url: 'mongoFile/upload'
		    ,accept: 'file'
		    ,done: function(data){
		      if(data.code == 0){ //上传成功
		    	 layer.msg("上传成功！")
		      }
		    }
		    ,error: function(){
		    	layer.msg("上传失败！")
		    }
		  });
		});
}
</script>

</body>
</html>