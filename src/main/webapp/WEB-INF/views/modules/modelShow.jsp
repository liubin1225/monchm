<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
  String path=request.getContextPath();
  String basePath =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>editor</title>
    <script src="<%= basePath %>commons/js/jquery/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="<%= basePath %>commons/css/material/materialize.min.css">
    <link href="<%= basePath %>commons/fonts/material/material-icons.css" rel="stylesheet" type="text/css">
    <script src="<%= basePath %>commons/js/colorpicker/colorpicker.min.js"></script>
    <script src="<%= basePath %>commons/js/material/materialize.min.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/three.min-r84.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/DDSLoader.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/MTLLoader.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/OBJLoader.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/OrbitControls.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/ImageLoader.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/stats.min.js"></script>
    <script src="<%= basePath %>commons/js/viewmodel/ZipLoader.js"></script>
</head>

<body>


    <div id="editor" class="row">
        <div id="editor-view" class="col m3" style="padding-left: 0px; padding-right: 0px; padding-top: 0px">
        <span>模型名称</span><div>${modelMaster.modelName }</div>
        <span>模型简介</span><div>${modelMaster.modelDescription }</div>
        </div>
        <div id="editor-view" class="col m9" style="padding-left: 0px;padding-top: 0px;padding-right : 0px">
            <div class="col m12" id="container" style="padding-left: 0px;padding-right: 0px;padding-top: 0px">

            </div>
        </div>
      </div>
        <script type="text/javascript" src="<%= basePath %>commons/js/test.js"></script>
        <script type="text/javascript">
	        /* url, vrkb, mtl, obj, far, near, fov,scale,directionalLight,ambientLight,
			positionX,positionY,positionZ,rotationX,rotationY,rotationZ,bgFlag,bgValue */
			var bgFlag = 0,bgValue = '';
			 if('${modelMaster.backgroundCubeId.modelCubeUrl}' != null && '${modelMaster.backgroundCubeId.modelCubeUrl}' != ''){
		        	bgFlag = 1;
		        	bgValue = '${modelMaster.backgroundCubeId.modelCubeUrl}';
		        }
	        if('${modelMaster.backgroundPicId.modelPicUrl}' != null && '${modelMaster.backgroundPicId.modelPicUrl}' != ''){
	        	bgFlag = 2;
	        	bgValue = '${modelMaster.backgroundPicId.modelPicUrl}'
	        }
	        if(('${modelMaster.backgroundColor}' != null) && ('${modelMaster.backgroundColor}' != '')){
				bgFlag = 3;
				bgValue = '${modelMaster.backgroundColor}';
	        }
            var test = new Zip3DShow('<%= basePath %>cache/'+'${ModelId}'+'/','${vrkb}','${mtl}','${obj}','','','','${zoom/100}','${modelMaster.directionalLight}',
            		'${modelMaster.ambientLight}','${modelMaster.positionX}','${modelMaster.positionY}','${modelMaster.positionZ}','${modelMaster.rotationX}',
            		'${modelMaster.rotationY}','${modelMaster.rotationZ}',bgFlag,bgValue);
            test.load('${compressSize}');
            test.setAxis(false);
            test.animate();
            $(function(){
                $('#editor-panel').height(window.innerHeight+'px');
 		        test.startEffects();
            });


        </script>
    
</body>

</html>