<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/modules/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="default"/>
<title>视频上传</title>

</head>
<body>
	<form name="form1" id="form1" action="<%=request.getContextPath()%>/mongoVideo/videoUpload" method="post" enctype="multipart/form-data">
		<blockquote class="layui-elem-quote">视频上传</blockquote>
		<hr class="layui-bg-red">
		<c:if test="${message != null}">
			<fieldset class="layui-elem-field" id="fmess">
			  <legend>提示信息</legend>
			  <div class="layui-field-box">
			    ${message}
			  </div>
			</fieldset>
		</c:if>
		<blockquote class="layui-elem-quote layui-quote-nm">
		 	<div style="padding-bottom: 5px;" >
				<input  type="file" size="48" name="filedata" id='filedata'>
			</div>

			<div id="progressBar" style="display: none;">

				<div id="theMeter">
					<div id="progressBarText"></div>

					<div class="layui-progress layui-progress-big" id="progressBarBox"
						style="color: Silver; border-width: 1px; border-style: Solid; width: 500px; TEXT-ALIGN: left">
						<div id="progressBarBoxContent" class="layui-progress-bar layui-bg-blue" style="width: 0%;"></div>
					</div>
					<div id="progress-content" style="display: none;"></div>
				</div>
			</div>
		</blockquote>
		
		<button type="button" class="layui-btn" id="submitButton" onclick='fSubmit();'><i class="layui-icon"></i>上传视频</button> 
	</form>

<SCRIPT type=text/javascript>
	layui.use(['table','element'], function(){
		$("#mongoVideoUpload").addClass("layui-this");
	})
	function doProgressLoop(prog, max, counter) {
		var x = document.getElementById('progress-content').innerHTML;
		var y = parseInt(x);
		if (!isNaN(y)) {
			prog = y;
		}
		counter = counter + 1;
		if (prog < 100) {
			setTimeout("getProgress()", 1000);
			setTimeout("doProgressLoop(" + prog + "," + max + "," + counter
					+ ")", 1500);
			document.getElementById('progressBarText').innerHTML = '视频上传中: '
					+ prog + '%';
			document.getElementById('progressBarBoxContent').style.width = parseInt(prog)
					+ '%';

		}
	}

	function getProgress() {
		$.ajax({
			type : "post",
			dataType : "json",
			url : "${pageContext.request.contextPath}/progress",
			async : true,//使用同步的方式,true为异步方式
			success : function(data) {
				document.getElementById('progress-content').innerHTML = data;
			},
			error : function() {
				document.getElementById('progressBarText').innerHTML = "视频上传中";
			}
		});
	}

	function fSubmit() {
		var filedata = $("#filedata");
		var filetxt = "请选择需要上传的视频文件!"
		
        if($.trim(filedata.val()) == ''){
            window.wxc.xcConfirm(filetxt, window.wxc.xcConfirm.typeEnum.info);
            return false;
     	}else{
     		var filepath = $.trim(filedata.val());
     		var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart, filepath.length).toUpperCase();
            if (ext != ".MP4" && ext != ".RMVB" && ext != ".AVI" && ext != ".RM" && ext != ".WMV") {
            	window.wxc.xcConfirm("视频格式不正确，请重新上传！", window.wxc.xcConfirm.typeEnum.info);
                return false;
            }
     	}
		
		$("#fmess").css('display','none'); 
        
		var button = window.document.getElementById("submitButton");
		var DISABLED = 'layui-btn-disabled';
		//button.className(DISABLED);
		$("#submitButton").addClass(DISABLED);
		button.disabled = true;
		var max = 100;
		var prog = 0;
		var counter = 0;
		document.getElementById('progressBar').style.display = 'block';
		document.getElementById('progressBarText').innerHTML = '视频上传中: 0%';
		getProgress();
		doProgressLoop(prog, max, counter);
		document.getElementById("form1").submit();
	}
</SCRIPT>
</body>
</html>