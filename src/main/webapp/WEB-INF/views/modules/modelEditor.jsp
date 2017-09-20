<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
	 <nav style="background-color: #333333 ">
        <div class="nav-wrapper">
            <div class="brand-logo">
                <a  >&nbsp;&nbsp;&nbsp;&nbsp;正在编辑</a>
                <a href="#">&nbsp;模型名称</a>
            </div>
            <ul id="nav-mobile" class="right hide-on-med-and-down">
                <li><a href="#" class="btn orange waves-effect waves-light">退出</a></li>
                <li><a  onclick="save()" class="btn waves-effect waves-light">保存</a></li>
            </ul>
        </div>
    </nav>
	
    <div id="editor" class="row" >
        <div id="editor-panel" class="col m3 " style="background-color: #595959;padding-left: 0px; padding-right: 0px; padding-top: 0px">
            <ul class="collapsible" data-collapsible="accordion">
                <li>
                    <div class="collapsible-header"  onclick="changeIcon(0)" style="background-color: #333333"><i class="material-icons" >keyboard_arrow_right</i><span style="color: white">坐标设置</span></div>
                    <div class="collapsible-body " style="background-color: #595959">
                        <div class="row">
                            <div class="col m3">
                                <label class="row">X</label>
                                <div class="row">
                                    <input type="text" id="xPosition" value="${modelMaster.positionX }" class="col m12">
                                </div>

                            </div>

                            <div class="col m3">
                                <label>Y</label>
                                <div class="row">
                                    <input type="text" id="yPosition" value="${modelMaster.positionY }" class="col m12">
                                </div>
                            </div>

                            <div class="col m3">
                                <label>Z</label>
                                <div class="row">
                                    <input type="text" id="zPosition" value="${modelMaster.positionZ }" class="col m12">
                                </div>
                            </div>
                            <button class="btn col m3" onclick="setPosition()">确认</button>
                        </div>

                    </div>
                </li>
                <li>
                    <div class="collapsible-header"  onclick="changeIcon(1)" style="background-color: #333333"><i class="material-icons" >keyboard_arrow_right</i><span style="color: white">旋转设置</span></div>
                    <div class="collapsible-body " style="background-color: #595959">
                        <div class="row">
                            <div class="col m4 row">
                                <a class=" waves-effect waves-light bule col m4" onclick="rotateLeftX()"><i class="material-icons">keyboard_arrow_left</i></a>
                                   <span class="col m2">X</span>
                                <a class="waves-effect waves-light bule col m4" onclick="rotateRightX()"><i class="material-icons">keyboard_arrow_right</i></a>
                            </div>

                            <div class="col m4 row">
                                <a class=" waves-effect waves-light bule col m4" onclick="rotateLeftY()"><i class="material-icons">keyboard_arrow_left</i></a>
                                <span class="col m2">Y</span>
                                <a class=" waves-effect waves-light bule col m4 " onclick="rotateRightY()"><i class="material-icons">keyboard_arrow_right</i></a>
                            </div>

                            <div class="col m4 row">
                                <a class=" waves-effect waves-light bule col m4 left-align" onclick="rotateLeftZ()"><i class="material-icons">keyboard_arrow_left</i></a>
                                <span class="col m2">Z</span>
                                <a class=" waves-effect waves-light bule col m4 " onclick="rotateRightZ()"><i class="material-icons">keyboard_arrow_right</i></a>
                            </div>

                        </div>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"  onclick="changeIcon(2)" style="background-color: #333333"><i class="material-icons" >keyboard_arrow_right</i><span style="color: white">光强设置</span></div>
                    <div class="collapsible-body " style="background-color: #595959">
                            <div>
                                <p class="range-field">
                                    <label>平行光光强</label>
                                    <input type="range" id="directional-light-intensity" value="${modelMaster.directionalLight }"  min="0" max="2" step="0.05" oninput="setDirectionalLight(this)"/>
                                </p>
                            </div>

                            <div >
                                <p class="range-field">
                                    <label>环境光光强</label>
                                    <input type="range" id="ambient-light-intensity" value="${modelMaster.ambientLight }"  min="0" max="2" step="0.05" oninput="setAmbientLight(this)"/>
                                </p>
                            </div>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"  onclick="changeIcon(3)" style="background-color: #333333"><i class="material-icons" >keyboard_arrow_right</i><span style="color: white">大小设置</span></div>
                    <div class="collapsible-body " style="background-color: #595959">
                        <div class="row">
                            <a class=" waves-effect waves-light bule col m3 right-align" onclick="subScale()"><i class="material-icons">remove</i></a>
                            <input type="text" class="col m3 " value="${zoom/100 }" id="scale" >
                            <a class=" waves-effect waves-light bule col m3" onclick="addScale()"><i class="material-icons" >add</i></a>
                            <button class="btn col m3" onclick="setScale()">确认</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"  onclick="changeIcon(4)" style="background-color: #333333"><i class="material-icons" >keyboard_arrow_right</i><span style="color: white">坐标轴设置</span></div>
                    <div class="collapsible-body " style="background-color: #595959">
                        <div class="switch">
                            <label>
                                Off
                                <input type="checkbox" checked="checked" onclick="setAxis(this)">
                                <span class="lever"></span>
                                On
                            </label>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="collapsible-header"  onclick="changeIcon(5)" style="background-color: #333333"><i class="material-icons" >keyboard_arrow_right</i><span style="color: white">背景设置</span></div>
                    <div class="collapsible-body " style="background-color: #595959">
                        <div class="row">
                            <div class="col m12">
                                <ul class="tabs">
                                    <li class="tab col m4 "><a class="active" href="#cube-texture">体纹理</a></li>
                                    <li class="tab col m4"><a  href="#image-texture">图片</a></li>
                                    <li class="tab col m4"><a href="#bg-color">颜色</a></li>
                                    <div class="indicator col s4" ></div>
                                </ul>
                            </div>
                            <div id="cube-texture" class="col m12 ">
                                <ul class="tabs">
                                    <li class="tab col m3 "><a  href="#" class="active" onclick="setCube('commons/textures/pisa/')" >1</a></li>
                                    <li class="tab col m3"><a  href="#"onclick="setCube('commons/textures/MilkyWay/')">2</a></li>
                                    <li class="tab col m3"><a href="#" onclick="setCube('commons/textures/skybox/')">3</a></li>
                                    <li class="tab col m3"><a href="#" onclick="setCube('commons/textures/Park2/')">4</a></li>
                                    <div class="indicator col m3" ></div>
                                </ul>
                            </div>
                            <div id="image-texture" class="col m12 ">
                                <ul class="tabs">
                                    <li class="tab col m3 "><a class="active" href="#" onclick="setTexture('commons/textures/a.jpg')">a</a></li>
                                    <li class="tab col m3"><a  href="#" onclick="setTexture('commons/textures/b.jpg')">b</a></li>
                                    <li class="tab col m3"><a href="#" onclick="setTexture('commons/textures/c.png')">c</a></li>
                                    <li class="tab col m3"><a href="#" onclick="setTexture('commons/textures/d.jpg')">d</a></li>
                                    <div class="indicator col m3" ></div>
                                </ul>
                            </div>
                            <div id="bg-color" class="col m12 row">
                                <div id="picker-wrapper">
                                    <div id="picker"></div>
                                    <div id="picker-indicator"></div>
                                </div>
                                <div id="slider-wrapper">
                                    <div id="slider"></div>
                                    <div id="slider-indicator"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div id="editor-view" class="col m9" style="padding-left: 0px;padding-top: 0px;padding-right : 0px">
            <div class="col m12" id="container" style="padding-left: 0px;padding-right: 0px;padding-top: 0px">

            </div>
        </div>
        <script type="text/javascript" src="<%= basePath %>commons/js/test.js"></script>
        <script type="text/javascript">

            $(function(){
                $('#editor-panel').height(window.innerHeight+'px');
                $('#head').height(window.innerHeight/16+'px');
            });
            //1代表cube	2代表texture	3代表color
			var bgFlag = 1;
            var iContext = $("li > div > i");
            var preNum = -1;
            function changeIcon(num){
                if(num == preNum){
                    if(iContext[num].innerText == "keyboard_arrow_right"){
                        iContext[num].innerText = "keyboard_arrow_down";
                    }else{
                        iContext[num].innerText = "keyboard_arrow_right";
                    }
                }else {
                    iContext[num].innerText = "keyboard_arrow_down";
                    if (preNum >= 0) {
                        iContext[preNum].innerText = "keyboard_arrow_right";
                    }
                    preNum = num;
                }
            }
            var bgFlag2 = 0,bgValue = '';
			 if('${modelMaster.backgroundCubeId.modelCubeUrl}' != null && '${modelMaster.backgroundCubeId.modelCubeUrl}' != ''){
		        	bgFlag2 = 1;
		        	bgValue = '${modelMaster.backgroundCubeId.modelCubeUrl}';
		        }
	        if('${modelMaster.backgroundPicId.modelPicUrl}' != null && '${modelMaster.backgroundPicId.modelPicUrl}' != ''){
	        	bgFlag2 = 2;
	        	bgValue = '${modelMaster.backgroundPicId.modelPicUrl}'
	        }
	        if(('${modelMaster.backgroundColor}' != null) && ('${modelMaster.backgroundColor}' != '')){
				bgFlag2 = 3;
				bgValue = '${modelMaster.backgroundColor}';
	        }
            /* url, vrkb, mtl, obj, far, near, fov,scale,directionalLight,ambientLight,
			positionX,positionY,positionZ,rotationX,rotationY,rotationZ,bgFlag,bgValue */
           var test = new Zip3DShow('<%= basePath %>cache/'+'${ModelId}'+'/','${vrkb}','${mtl}','${obj}','','','','${zoom/100}','${modelMaster.directionalLight}',
           		'${modelMaster.ambientLight}','${modelMaster.positionX}','${modelMaster.positionY}','${modelMaster.positionZ}','${modelMaster.rotationX}',
        		'${modelMaster.rotationY}','${modelMaster.rotationZ}',bgFlag2,bgValue);
            test.load('${compressSize}');
            test.animate();
            test.getStats().dom.style.marginTop=window.innerHeight/16+'px';
            test.setAxis(true);
            test.startEffects();
		

            function setAxis(me){
                if(me.checked == true){
                    test.setAxis(true);
                }else{
                    test.setAxis(false);
                }
            }

            function rotateLeftZ(){
                test.setObjectRotateZ(Math.PI/2);
            }

            function rotateRightZ(){
                test.setObjectRotateZ(-Math.PI/2);
            }

            function rotateLeftY(){
                test.setObjectRotateY(-Math.PI/2);
            }

            function rotateRightY(){
                test.setObjectRotateY(Math.PI/2);
            }

            function rotateLeftX(){
                test.setObjectRotateX(Math.PI/2);
            }

            function rotateRightX(){
                test.setObjectRotateX(-Math.PI/2);
            }

            function setDirectionalLight(me){
                test.setDirectionalLight(me.value);
            }

            function setAmbientLight(me){
                test.setAmbientLight(me.value);
            }

            function setCube(url){
                test.setCube(url);
                bgFlag = 1;
            }

            function setTexture(url){
                test.setTexture(url);
                bgFlag = 2;
            }

            function addScale(){
                var value = $('#scale').val();
                if(value == '' || value == undefined || value == null)
                    value = $('#scale').val(1);
                $('#scale').val((parseFloat( $('#scale').val()) +0.1).toFixed(5));
            }

            function subScale(){
                var value = $('#scale').val();
                if(value == '' || value == undefined || value == null)
                    value = $('#scale').val(1);
                $('#scale').val((parseFloat( $('#scale').val()) -0.1).toFixed(5));
            }

            function setScale(){
                var value = $('#scale').val();
                if(value == '' || value == undefined || value == null)
                    value = 1;
                test.setScale(value);
            }

            function setPosition(){
                var x = $('#xPosition').val();
                var y = $('#yPosition').val();
                var z = $('#zPosition').val();
                if(x == null || x == '' || x == undefined)
                    x = 0;
                if(y == null || y == '' || y == undefined)
                    y = 0;
                if(z == null || z == '' || z == undefined)
                    z = 0;
                test.setObjectPosition(x,y,z);

            }

            ColorPicker.fixIndicators(
                    document.getElementById('slider-indicator'),
                    document.getElementById('picker-indicator'));

            ColorPicker(
                    document.getElementById('slider'),
                    document.getElementById('picker'),

                    function(hex, hsv, rgb, pickerCoordinate, sliderCoordinate) {

                        ColorPicker.positionIndicators(
                                document.getElementById('slider-indicator'),
                                document.getElementById('picker-indicator'),
                                sliderCoordinate, pickerCoordinate
                        );
                       // alert(e=hex);
                       test.setColor('rgb('+rgb.r+','+rgb.g+','+rgb.b+')');
                       bgFlag = 3;
                    });
			function save(){
				var obj = test.getObject3D();
				var bgPic,bgCube;
				var formData = new FormData();
				formData.append("positionX", obj.position.x+'');
				formData.append("positionY", obj.position.y+'');
				formData.append("positionZ", obj.position.y+'');
				formData.append("rotationX", obj.rotation.x+'');
				formData.append("rotationY", obj.rotation.y+'');
				formData.append("rotationZ", obj.rotation.z+'');
				formData.append("directionalLight", test.getDirectionalLightIntensity()+'');
				formData.append("ambientLight", test.getAmbientLightIntensity()+'');
				formData.append("zoom",obj.scale.x*100+'');
				if(bgFlag == 1){
					bgCube = test.getSceneBacground().image[0].src;
					formData.append("backgroundCubeId", bgCube.substring(bgCube.indexOf('MongoAPI')+9,bgCube.length-6));
				}
				if(bgFlag == 2){
					bgPic=test.getSceneBacground().image.src;
					formData.append("backgroundPicId",bgPic.substr(bgPic.indexOf('MongoAPI')+9));
				}
				if(bgFlag == 3){
					formData.append("backgroundColor", test.getSceneBacground().getStyle());
				}
				$.ajax({
					type:'post',
					url:'saveModelSetting.action',
					data:formData,
					dataType:'json',
					processData: false,  // 不处理数据
					contentType: false,   // 不设置内容类型
					
				}).done(function(response) {
				     switch(response.status){
					     case 0:
					    	 alert('设置失败');
					     case 1:
					    	 alert('设置成功');
				     }
				 })
				.fail(function(data) {
					alert('error');
				});
			}
        </script>
    </div>
</body>

</html>