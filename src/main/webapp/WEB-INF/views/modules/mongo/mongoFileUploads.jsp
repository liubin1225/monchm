<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="default"/>
<title>多文件上传</title>
</head>
<body>
	<blockquote class="layui-elem-quote">多文件上传</blockquote>
	<hr class="layui-bg-red">
	<div class="layui-upload">
	  <button type="button" class="layui-btn layui-btn-normal" id="testList">选择多文件</button> 
	  <div class="layui-upload-list">
	    <table class="layui-table">
	      <thead>
	        <tr><th>文件名</th>
	        <th>大小</th>
	        <th>状态</th>
	        <th>操作</th>
	      </tr></thead>
	      <tbody id="demoList"></tbody>
	    </table>
	  </div>
	  <button type="button" class="layui-btn" id="testListAction">开始上传</button>
	</div>
<script>
$(function(){
	$("#wjzl").addClass("layui-nav-itemed");
	$("#mongoFileUploads").addClass("layui-this layui-nav-tree layui-nav-side");
	monupload();
})
function monupload(){
	layui.use(['layer','upload','element'], function(){
		  var upload = layui.upload;
		  //多文件列表示例
		  var demoListView = $('#demoList')
		  ,uploadListIns = upload.render({
		    elem: '#testList'
		    ,url: 'mongoFile/upload'
		    ,accept: 'file'
		    ,multiple: true
		    ,auto: false
		    ,bindAction: '#testListAction'
		    ,choose: function(obj){   
		      var files = obj.pushFile(); //将每次选择的文件追加到文件队列
		      alert(files);
		      //读取本地文件
		      obj.preview(function(index, file, result){
		        var tr = $(['<tr id="upload-'+ index +'">'
		          ,'<td>'+ file.name +'</td>'
		          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
		          ,'<td>等待上传</td>'
		          ,'<td>'
		            ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
		            ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
		          ,'</td>'
		        ,'</tr>'].join(''));
		        
		        //单个重传
		        tr.find('.demo-reload').on('click', function(){
		          obj.upload(index, file);
		        });
		        
		        //删除
		        tr.find('.demo-delete').on('click', function(){
		          delete files[index]; //删除对应的文件
		          tr.remove();
		        });
		        
		        demoListView.append(tr);
		      });
		    }
		    ,done: function(data, index, upload){
		      if(data.code == 0){ //上传成功
		        var tr = demoListView.find('tr#upload-'+ index)
		        ,tds = tr.children();
		        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
		        tds.eq(3).html(''); //清空操作
		        delete files[index]; //删除文件队列已经上传成功的文件
		        return false;
		      }
		      this.error(index, upload);
		    }
		    ,error: function(index, upload){
		      var tr = demoListView.find('tr#upload-'+ index)
		      ,tds = tr.children();
		      tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
		      tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
		    }
		  });
		});
}
</script>

</body>
</html>