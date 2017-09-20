<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="layui-header">
	<div class="layui-logo"><a class="layui-logo" href="index">monchm</a></div>
	<!-- 头部区域（可配合layui已有的水平导航） -->
	<ul class="layui-nav layui-layout-right">
		<li class="layui-nav-item"><a href="http://api.mongodb.com/java/" target="_blank">MongoDB API</a></li>
		<li class="layui-nav-item"><a href="">个人中心<span
				class="layui-badge-dot"></span></a></li>
		<li class="layui-nav-item"><a href=""><img
				src="http://t.cn/RCzsdCq" class="layui-nav-img">战华少</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="javascript:;">修改信息</a>
				</dd>
				<dd>
					<a href="javascript:;">安全管理</a>
				</dd>
				<dd>
					<a href="javascript:;">退了</a>
				</dd>
			</dl></li>
	</ul>
</div>
<div class="layui-side layui-bg-black">
	<div class="layui-side-scroll">
		<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
		<ul class="layui-nav layui-nav-tree layui-inline" lay-filter="demo"
			style="margin-right: 10px;">
			<li id="wjzl" class="layui-nav-item"><a href="javascript:;">文件专栏</a>
				<dl class="layui-nav-child">
					<dd id="mongoFileList">
						<a href="mongoFileList">文件列表</a>
					</dd>
					<dd id="mongoFileUploads">
						<a href="mongoFileUploads">多文件上传</a>
					</dd>
					<dd id="mongoFileFolder">
						<a href="mongoFileFolder">浏览本地文件夹上传</a>
					</dd>
					<dd id="mongoFileDrag">
						<a href="mongoFileDrag">文件拖拽上传</a>
					</dd>
				</dl>
			</li>
			<li id="spzl" class="layui-nav-item layui-nav-itemed"><a href="javascript:;">视频专栏</a>
				<dl class="layui-nav-child">
					<dd id="mongoVideoUpload">
						<a href="mongoVideoUpload">视频上传</a>
					</dd>
					<dd id="mongoVideoList">
						<a href="mongoVideoList">视频列表</a>
					</dd>
				</dl>
			</li>
			<li class="layui-nav-item" id="mongoTreeList"><a href="mongoTreeList">树形菜单</a></li>
			<li class="layui-nav-item" id="scanlogtop"><a href="scanLog">系统日志</a></li>
			<li id="gncs" class="layui-nav-item layui-nav-itemed"><a href="javascript:;">功能测试</a>
				<dl class="layui-nav-child">
					<dd id="ddz">
						<a href="${pageContext.request.contextPath}/static/ddz/index.html" target="_blank">小游戏</a>
					</dd>
					<dd id="solrSearch">
						<a href="solrSearch">文件检索</a>
					</dd>
					<dd id="mongoForm">
						<a href="mongoForm">表单</a>
					</dd>
				</dl>
			</li>
		</ul>
	</div>
</div>
