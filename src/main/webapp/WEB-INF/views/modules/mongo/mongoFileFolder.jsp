<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta name="decorator" content="default"/>
<title>浏览本地文件夹上传</title>
</head>
<body>
	<blockquote class="layui-elem-quote">文件上传</blockquote>
	<hr class="layui-bg-red">
  	<form class="layui-form" action="">
  		<blockquote class="layui-elem-quote layui-quote-nm">
		<div class="layui-form-item">
		    <label class="layui-form-label" style="width: 100px;">文件夹路径：</label>
		    <div class="layui-input-block">
		      <input type="text" name="filepath" id="filepath" lay-verify="filepath" autocomplete="off" placeholder="请输入文件夹路径" class="layui-input" style="width: 500px;">
		    </div>
		</div>
		</blockquote>
	    <button type="button" class="layui-btn" id="tjbutton" lay-submit="" lay-filter="demo1"><i class="layui-icon"></i>上传文件</button>
	</form>
<script>
layui.use(['layer','upload','element','form'], function(){
	$("#wjzl").addClass("layui-nav-itemed");
	$("#mongoFileFolder").addClass("layui-this layui-nav-tree layui-nav-side");
	var layer = layui.layer,
	form = layui.form;
	
	//自定义验证规则
  	form.verify({
  		filepath: function(value){
      		if(value.length == 0){
        		return '文件路径不能为空!';
      		}
    	}
  	});
	
  	//监听提交
    form.on('submit(demo1)', function(data){
    	var DISABLED = 'layui-btn-disabled';
    	$("#tjbutton").addClass(DISABLED);
    	$.ajax({
            type: "POST",
            url:"mongoFile/mongoFileFolder",
            data:{filepathv:$("#filepath").val()},
            error: function(request) {
            	layer.msg("上传失败！");
            },
            success: function(data) {
            	if(data.code === 0){
            		layer.open({
           			  title: '信息提示'
           			  ,content: '上传成功！'
           			}); 
            		$("#tjbutton").removeClass(DISABLED);
            	}else{
            		layer.open({
           			  title: '信息提示'
           			  ,content: '上传失败！'
           			}); 
            		$("#tjbutton").removeClass(DISABLED);
            	}
            }
        });
      	return false;
    });
	
});
</script>

</body>
</html>