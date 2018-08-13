(function($) {
	var securityKey = "";
	var uploadUrl = "";
	var appId = "";
	var path = $("#path").val();
	//获取上传参数
	$.ajax({
        cache: true,
        type: "POST",
        url:path+'/upload/getValue',
        //data:$('#inputForm').serialize(),// 你的formid
        async: false,
        error: function(request) {
        },
        success: function(data) {
        	var Data = eval('(' + data + ')');
			if(Data.code == 0){
				uploadUrl = Data.uploadUrl;
				appId = Data.appId;
				securityKey = Data.securityKey;
			}
        }
    });
	$(function() {
		var log = (function() {
			var dom = $('#log');

			return function(str) {
				dom.append('<p>' + str + '</p>')
			}
		})();
		// 断点续传js控制
		var fileMd5;
		var size = 0;
		var chunks = 0;
		var fileName;
		var uploadStart = 0;
		var chunk = 0;
		var chunkMd5;
		var chunkSize = 2097152;
		WebUploader.Uploader.register({
			"before-send-file" : "beforeSendFile",
			"before-send" : "beforeSend",
			"after-send-file" : "afterSendFile",
		}, {

			// 时间点1：所有分块进行上传之前调用此函数
			beforeSendFile : function(file) {
				deferred = WebUploader.Deferred();
				size = file.size;
				timestamp= Date.parse(new Date())/1000;
				fileName = file.name;
				
				// 1、计算文件的唯一标记，用于断点续传
				(new WebUploader.Uploader()).md5File(file, 0, 10 * 1024 * 1024)
						.progress(function(percentage) {
							$('#state').find("p").text("正在读取文件信息...");
						}).then(function(val) {
							fileMd5 = val;
							signParam = "{chunkSize="+chunkSize+", fileMd5="+fileMd5+", size="+size+", timestamp="+timestamp+"}";
							sign = Base64.encode(CryptoJS.HmacSHA1(signParam, securityKey));
							// 获取断点续传位置
							$.ajax({
								type : "POST",
								url : uploadUrl+"/chunk/check",
								data : {
									// 文件大小
									size : size,
									// 文件唯一标记
									fileMd5 : fileMd5,
									// 切片大小
									chunkSize : chunkSize,
									//签名
									sign : sign,
									//应用分配id
									appId : appId,
									//当前时间戳
									timestamp : timestamp
									
								},
								dataType : "json",
								error:function(XMLHttpRequest, textStatus, errorThrown){
									$('#state').find("p").text(fileName+"上传失败...");
								},
								success : function(response) {
									if (response.responseCode == 0) {
										uploadStart = response.uploadStart;
										chunk = response.chunk;
									} else {
										// 校验错误,从新发起校验
										deferred.resolve();
									}
								}
							});
							$('#state').find("p").text("上传中..");
							// 获取文件信息后进入下一步
							deferred.resolve();
						});

				return deferred.promise();
			},
		    //时间点2:获取md5
			beforeSend : function(block) {
				var deferred = WebUploader.Deferred();
				uploader.md5File(block.blob)
					.then(function(val) {
						chunkMd5 = val;
						deferred.resolve();
					})
				return deferred.promise();
			},
			// 时间点3：所有分块上传成功后调用此函数
			afterSendFile : function() {
				timestamp= Date.parse(new Date())/1000;
				signParam = "{chunkSize=" + chunkSize + ", fileMd5=" + fileMd5 + ", fileName=" + fileName + ", size=" + size + ", timestamp=" + timestamp + "}";
				sign = Base64.encode(CryptoJS.HmacSHA1(signParam, securityKey));
				// 如果分块上传成功，则通知后台合并分块
				$.ajax({
					type : "POST",
					url : uploadUrl + "/chunk/mergeFile",
					data : {
						appId: appId,
						fileMd5: fileMd5,
						fileName: fileName,
						chunkSize:chunkSize,
						sign: sign,
						size : size,
						timestamp : timestamp
					},
					success : function(response) {
						if(response.responseCode == 0 && response.filePath != null){
							$("#videoUrl").val(response.filePath);
							/*var path = "uploads/" + md5 + ".mp4";
							$("#state").attr("src", path);*/
                            uploader.reset();;
							$('#state').find("p").text(fileName+"上传成功...");
						}else{
							$('#state').find("p").text(fileName+"上传失败...");
						}
						
					}
				});
			}
		});
		var uploader = WebUploader.create({
			// swf文件路径
			swf : 'static/webuploader-0.1.5/Uploader.swf',
			// 文件接收服务端。
			server : uploadUrl + '/chunk/upload',
			// 定义选择按钮
			pick : '#filePicker',
			// 自动上传
			auto : false,
			// 禁止浏览器打开文件
			disableGlobalDnd : true,
			// 添加截图功能
			paste : '#wrapper',
			// 定义拖动面板
			dnd : '#wrapper',
			// 分片上传
			chunked : true,
			// 分片大小为2M
			chunkSize : 2097152,
			// 分片上传失败重试3次
			chunkRetry : 3,
			// 图片不做压缩
			compress : false,
			//限制数量
			fileNumLimit : 2,
			//限制文件大小20M   20971520
			fileSingleSizeLimit : 209715200,
			accept :{
				title : "mp4",
				extensions : "mp4"
			}
		});
		uploader.on( 'uploadBeforeSend', function( block, data ) {
			timestamp= Date.parse(new Date())/1000;
			data.appId = appId;
			data.chunk = block.chunk;
			data.chunkSize = chunkSize;
            data.fileMd5 = fileMd5;
            data.timestamp = timestamp;
            data.chunkMd5 = chunkMd5;
			signParam = '{chunk=' + block.chunk + ', chunkMd5=' + chunkMd5 + ', chunkSize=' + chunkSize + ', fileMd5=' + fileMd5 + ', size=' + size +', timestamp=' + data.timestamp +'}';
			signTemp = CryptoJS.HmacSHA1(signParam, securityKey);
			sign = Base64.encode(signTemp);
			data.sign = sign;
		},2);
		uploader.on('error',function(type){
			if(type=='F_EXCEED_SIZE'){
				layer.alert("请上传小于200M的视频文件");
			}
			if(type =='Q_TYPE_DENIED'){
				layer.alert("文件格式错误，请选择mp4文件");
			}
		});
		// 当有文件被添加进队列的时候
		uploader.on( 'fileQueued', function( file ) {
			var sb = uploader.getFiles();
			if(sb.length>1){
				uploader.removeFile(sb[0].id,true);
			}
			$("#videoUrl").val("");
			$('#state').find("p").text(file.name+"等待上传...");
		});
		
		$('#ctlBtn').on( 'click', function() {
            var sb = uploader.getFiles();
            if(sb.length == 0){
                layer.alert("请上传小于200M的视频文件");
            }
	        if ( state === 'uploading' ) {
	            uploader.stop();
	        }  else {
	            uploader.upload();
	        }
	    });
		uploader.on('uploadProgress', function(file, percentage) {
			console.log("Percentage:" + percentage * 100 + "%");
		});
	});
})(jQuery);