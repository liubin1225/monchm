(function(global, factory) {
	typeof exports === 'object' && typeof module !== 'undefined' ?
		module.exports = factory() : typeof define === 'function' && define.amd ? define(factory) : (global.Zip3DShow = factory());
}(this, (function() {
	'use strict';

	var camera, scene, stats, renderer, controls, zipLoader, objectTemp, light, light2, light3, light4, axisHelper;
	var mouseX = 0, mouseY = 0, cubeTextureLoader,textureLoader ,container, defaultScale;

	var Zip3DShow = function() {

		function Zip3DShow(url, vrkb, mtl, obj, far, near, fov,scale,directionalLight,ambientLight,
				positionX,positionY,positionZ,rotationX,rotationY,rotationZ,bgFlag,bgValue) {
			this._url = url;
			this._vrkb = url + vrkb;
			this._mtl = mtl;
			this._obj = obj;
			this._far = !far ? 1e10 : far;
			this._near = !near ? 0.1 : near;
			this._fov = !fov ? 100 : fov;
			defaultScale = !scale ? 1 : parseFloat(scale);
			this._directionalLight = !directionalLight ? 0.5 : parseFloat(directionalLight);
			this._ambientLight = !ambientLight ? 1 : parseFloat(ambientLight);
			this._positionX = !positionX ? 0 : parseFloat(positionX);
			this._positionY = !positionY ? 0 : parseFloat(positionY);
			this._positionZ = !positionZ ? 0 : parseFloat(positionZ);
			this._rotationX = !rotationX ? 0 : parseFloat(rotationX);
			this._rotationY = !rotationY ? 0 : parseFloat(rotationY);
			this._rotationZ = !rotationZ ? 0 : parseFloat(rotationZ);
			this._bgFlag = bgFlag;
			this._bgValue = bgValue;
			this.init();
		}

		Zip3DShow.prototype.init = function init() {
			container = document.getElementById('container');
			container.style.height = window.innerHeight + 'px';
			camera = new THREE.PerspectiveCamera(60, container.offsetWidth / container.offsetHeight, this._near, this._far);
			camera.fov = this._fov;
			camera.position.set(100, 200, 500);
			scene = new THREE.Scene();
			stats = new Stats();

			//初始化图片加载器
			textureLoader = new THREE.TextureLoader();
			//初始化cube加载器
			cubeTextureLoader = new THREE.CubeTextureLoader();

			container.appendChild(stats.dom);
			stats.dom.style.removeProperty('left');
			
			// LIGHTS
			light = new THREE.DirectionalLight(0xff5808, this._directionalLight);
			light.position.set(100, 140, 500); //上部光源
			light.position.multiplyScalar(1.1);
			light.color.setHSL(0.6, 0.075, 1);
			scene.add(light);

			light2 = new THREE.DirectionalLight(0xff5808, this._directionalLight);
			light2.position.set(0, -1, 0); //底部光源
			scene.add(light2);

			light3 = new THREE.DirectionalLight(0xff5808, this._directionalLight);
			light3.position.set(-100, 140, -500); //上部光源
			light3.position.multiplyScalar(1.1);
			light3.color.setHSL(0.6, 0.075, 1);
			scene.add(light3);

			light4 = new THREE.AmbientLight(0xff5808, this._ambientLight);
			light4.position.multiplyScalar(1.1);
			light4.color.setHSL(0.6, 0.075, 1);
			scene.add(light4);

			// RENDERER
			renderer = new THREE.WebGLRenderer({
				antialias: true,
				alpha: true
			});
			var devicePixelRatio = window.devicePixelRatio;
			renderer.setPixelRatio( devicePixelRatio );
			renderer.setSize(container.offsetWidth ,container.offsetHeight);
			container.appendChild(renderer.domElement);
			if(this._bgFlag == 1){
				if(this._bgValue == null || this._bgValue == undefined || this._bgValue == '')
					this._bgValue = 'commons/textures/MilkyWay/';
				scene.background = cubeTextureLoader
				.setPath( this._bgValue )
				.load( [
					'px.jpg',
					'nx.jpg',
					'py.jpg',
					'ny.jpg',
					'pz.jpg',
					'nz.jpg'
				] );
			}
			if(this._bgFlag == 2){
				if(this._bgValue == null || this._bgValue == undefined || this._bgValue == '')
					this._bgValue = 'commons/textures/a.jpg';
				scene.background = textureLoader
				.load(
						this._bgValue,
					function ( texture ) {
						var material = new THREE.MeshBasicMaterial( {
							map: texture
						} );
					}
				);
			}
			if(this._bgFlag == 3){
				if(this._bgValue == null || this._bgValue == undefined || this._bgValue == '')
					this._bgValue = 0x70706D;
				scene.background = new THREE.Color(this._bgValue);
			}
			

			// model
			var onProgressObj = function(xhr) {
				//test2 = xhr;
				if(xhr.lengthComputable) {
					var percentComplete = xhr.loaded / xhr.total * 100;
					console.log(Math.round(percentComplete, 2) + '% downloaded');
					if(Math.round(percentComplete, 2) == 100) {
						//alert("obj加载完成");
					}
				}

			};

			var onProgressMtl = function(xhr) {
				if(xhr.lengthComputable) {
					var percentComplete = xhr.loaded / xhr.total * 100;
					console.log(Math.round(percentComplete, 2) + '% downloaded');
					if(Math.round(percentComplete, 2) == 100) {
						//alert("mtl加载完成");
					}
				}
			};

			var onErrorObj = function(xhr) {
				alert("Obj文件加载出现问题");
				console.log("Obj文件加载出现问题");
			};

			var onErrorMtl = function(xhr) {
				alert("Mtl文件加载出现问题");
				console.log("Mtl文件加载出现问题");
			};

			THREE.Loader.Handlers.add(/\.dds$/i, new THREE.DDSLoader());
			var mtlLoader = new THREE.MTLLoader();

			var me = this;
			zipLoader = new ZipLoader(me._vrkb);
			zipLoader.on('load', function(e) {
				var obj = this.extractAsBlobUrl(me._obj, 'text/plain');
				mtlLoader.setPath(me._url);
				mtlLoader.load(
					me._mtl,
					function(materials) {
						//test = materials;
						materials.preload();
						var objLoader = new THREE.OBJLoader();
						objLoader.setMaterials(materials);
						objLoader.setPath('');
						objLoader.load(
							obj,
							function(object) {
								object.position.set(me._positionX, me._positionY, me._positionZ);
								//object.updateMatrix();
								object.scale.set(0,0,0);
								object.scale.set(0, 0, 0);
								object.rotateY(-Math.PI/2);
								object.rotateX(me._rotationX);
								object.rotateY(me._rotationY);
								object.rotateZ(me._rotationZ);
								object.traverse( function( node ) {
								    if( node.material instanceof THREE.MeshPhongMaterial) {
								        node.material.side = THREE.DoubleSide;
								        node.material.shininess=100;
								    }else if(node.material instanceof THREE.MultiMaterial){
								    	for(var i = 0;i < node.material.materials.length;i++){
								    		node.material.materials[i].side = THREE.DoubleSide;
								    		node.material.materials[i].shininess=100;
								    	}
								    }else if(node.material instanceof THREE.Mesh){
								    	node.material.side = THREE.DoubleSide;
								    }
								});
								objectTemp = object;
								scene.add(object);
							},
							onProgressObj,
							onErrorObj
						);
					},
					onProgressMtl,
					onErrorMtl
				);

			});

			axisHelper = new THREE.AxisHelper(100);
			
			window.addEventListener('resize', function(e) {
				camera.aspect = container.offsetWidth / container.offsetHeight;
				camera.updateProjectionMatrix();
				renderer.setSize( container.offsetWidth , container.offsetHeight );
			}, false); //页面窗体的监听
			//旋转控制
			controls = new THREE.OrbitControls(camera, renderer.domElement);
			controls.target.set(0, 0, 0);
			controls.update();
			//scene.add(new THREE.AxisHelper(100));
			document.addEventListener( 'mousemove', function(event){
				mouseX = ( event.clientX - container.offsetWidth / 2 ) * 10;
				mouseY = ( event.clientY - container.offsetHeight /2 ) * 10;
			}, false );//增加对鼠标事件的监听
			
			controls.addEventListener( 'change', function(){
				renderer.render( scene, camera );
			});//对渲染器更新进行监听
			
		};

		Zip3DShow.prototype.animate = function animate() {
			alert(requestAnimationFrame( animate ));
			controls.update();
			stats.update();
			renderer.render( scene, camera );
		};

		Zip3DShow.prototype.load = function load(compressSize) {
			zipLoader.load(parseInt(compressSize));
		};

		Zip3DShow.prototype.startEffects = function startEffects() {
			var count = 20;
			var initScale = 0;
			var step = (defaultScale - initScale) / (count+2);
			var rotateStep = Math.PI / (2 * count);
			var initRotate = 0;
			var timer = setInterval(function(){
				initScale = initScale + step;
				objectTemp.scale.set(initScale,initScale,initScale);
				objectTemp.rotateY(rotateStep);
				initRotate = initRotate + rotateStep;
				if(initRotate ==(Math.PI / 2 + rotateStep * 2)){
					clearInterval(timer);
				}
			},100);
		};

		//模型位置
		Zip3DShow.prototype.setObjectPosition = function setObjectPosition(x, y, z) {
			objectTemp.position.set(x, y, z);
		};
		//模型大小
		Zip3DShow.prototype.setScale = function setScale(scale) {
			objectTemp.scale.set(scale,scale,scale);
		};
		//模型绕X轴旋转
		Zip3DShow.prototype.setObjectRotateX = function setObjectRotateX(x) {
			objectTemp.rotateX(x);
		};
		//模型绕Y轴旋转
		Zip3DShow.prototype.setObjectRotateY = function setObjectRotateY(y) {
			objectTemp.rotateY(y);
		};
		//模型绕Z轴旋转
		Zip3DShow.prototype.setObjectRotateZ = function setObjectRotateZ(z) {
			objectTemp.rotateZ(z);
		};
		//设置平行光强度
		Zip3DShow.prototype.setDirectionalLight = function setDirectionalLight(intensity) {
			light.intensity = intensity;
			light2.intensity = intensity;
			light3.intensity = intensity;
		};
		//设置环境光强度
		Zip3DShow.prototype.setAmbientLight = function setAmbientLight(intensity) {
			light4.intensity = intensity;
		};
		//设置cube
		Zip3DShow.prototype.setCube = function setCube(url) {
			if(url == null || url == undefined || url == '')
				url = 'commons/textures/MilkyWay/';
			scene.background = cubeTextureLoader
			.setPath( url )
			.setPath( name )
			.load( [
				'px.jpg',
				'nx.jpg',
				'py.jpg',
				'ny.jpg',
				'pz.jpg',
				'nz.jpg'
			] );
			
		};

		//设置背景图
		Zip3DShow.prototype.setTexture = function setTexture(url) {
			if(url == null || url == undefined || url == '')
				url = 'commons/textures/a.jpg';
		Zip3DShow.prototype.setTexture = function setTexture(name) {
			scene.background = textureLoader
				.load(
					url,
					name,
					function ( texture ) {
						var material = new THREE.MeshBasicMaterial( {
							map: texture
						} );
					}
				);

		};

		//设置背景颜色
		Zip3DShow.prototype.setColor = function setColor(color) {
			if(color == null || color == undefined || color == '')
				color = 0x70706D;
			scene.background = new THREE.Color(color);
		};
		//设置是否显示坐标轴
		Zip3DShow.prototype.setAxis = function setAxis(isShowAxis) {
			if(isShowAxis == true){
				scene.add( axisHelper );
			}else{
				scene.remove( axisHelper );
			}
		};
		//返回模型对象
		Zip3DShow.prototype.getObject3D = function getObject3D(){
			return objectTemp;
		}
		//返回平行光的光强
		Zip3DShow.prototype.getDirectionalLightIntensity = function getDirectionalLightIntensity(){
			return light.intensity;
		}
		//返回环境光的光强
		Zip3DShow.prototype.getAmbientLightIntensity = function getAmbientLightIntensity(){
			return light4.intensity;
		}
		//返回场景的背景
		Zip3DShow.prototype.getSceneBacground = function getSceneBacground(){
			return scene.background;
		}
		//返回左上角的状态
		Zip3DShow.prototype.getStats = function getStats(){
			return stats;
		}
		
		
		return Zip3DShow;
	}();

	return Zip3DShow;
}})));