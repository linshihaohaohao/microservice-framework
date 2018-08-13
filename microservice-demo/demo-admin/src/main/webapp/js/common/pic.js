function fileChanged(e) {
    var path =  $("#path").val();
    var id =  e.getAttribute("id")
    var srcValue = "";
    lrz(e.files[0]).then(function (rst) {
        var loadIndex = parent.layer.load();
        // 把处理的好的图片给用户看看呗
        //alert(rst.base64);
        var img = new Image();
        img.src = rst.base64;
        img.onload = function () {
            //$("#icon-image").attr("src",rst.base64);
            var picType = e.id;
            if (picType == "uploadMainPic") {
                srcValue = "mainPic";
                var userhtml =
                    "<td id='" + srcValue + "td'><a class='style-button' checkOnClick='true' groupName='style' style='margin-right:4px;'>"
                    + "<div class='img-div'><div class='delbtn-div'><img class='delbtn'  src='" + path + "/images/del.png' title='删除' onclick='deleteBg(\"" + srcValue + "\",\"" + id +"\")'/></div>"
                    + "<div class='bg-div'><img id='" + srcValue + "' class='bg-img' style='width:100px;height:80px;margin-botton:4px;' src='" + rst.base64 + "' onclick='showBigImg(\"" + srcValue + "\")'/>"
                    + "<input type='hidden' name='hidmainPic' id='hid" + srcValue + "'/>"
                    + "</div></div></a></td>";
                $("#showMainPic").html(userhtml);

                var fileData = {};
                fileData.base64 = rst.base64;
                fileData.fileName = rst.origin.name;
                fileData.size = rst.origin.size;
                fileData.type = rst.origin.type;
                var url =path + "/upload/pic";
                $.ajax({
                    url: url,
                    data: fileData,
                    type: 'post',
                    async:false,
                    success: function (data) {
                        $("#hid" + srcValue).val(data);
                        parent.layer.close(loadIndex);


                    }
                });

            }
        };
        parent.layer.close(loadIndex);
        return rst;
    })
};

function fileChanged1(e) {
    var path =  $("#path").val();
    var id =  e.getAttribute("id")
    var srcValue = "mainPic1";
    lrz(e.files[0]).then(function (rst) {
        var loadIndex = parent.layer.load();
        // 把处理的好的图片给用户看看呗
        //alert(rst.base64);
        var img = new Image();
        img.src = rst.base64;
        img.onload = function () {
            //$("#icon-image").attr("src",rst.base64);
            var picType = e.id;
            if (picType == "uploadMainPic1") {
                var userhtml =
                    "<td id='" + srcValue + "td'><a class='style-button' checkOnClick='true' groupName='style' style='margin-right:4px;'>"
                    + "<div class='img-div'><div class='delbtn-div'><img class='delbtn'  src='" + path + "/images/del.png' title='删除' onclick='deleteBg(\"" + srcValue + "\",\"" + id +"\")'/></div>"
                    + "<div class='bg-div'><img id='" + srcValue + "' class='bg-img' style='width:100px;height:80px;margin-botton:4px;' src='" + rst.base64 + "' onclick='showBigImg1(\"" + srcValue + "\")'/>"
                    + "<input type='hidden' name='hidmainPic1' id='hid" + srcValue + "'/>"
                    + "</div></div></a></td>";
                $("#showMainPic1").html(userhtml);
                var fileData = {};
                fileData.base64 = rst.base64;
                fileData.fileName = rst.origin.name;
                fileData.size = rst.origin.size;
                fileData.type = rst.origin.type;
                var url =path + "/upload/pic";
                $.ajax({
                    url: url,
                    data: fileData,
                    type: 'post',
                    async:false,
                    success: function (data) {
                        console.log(data);
                        $("#hidmainPic1").val(data);
                        parent.layer.close(loadIndex);
                    }
                });
            }
        };

        return rst;
    })
};


function deleteBg(srcValue,e) {
    layer.confirm('确定删除图片？', function () {
        //为了 可以重复提交相同的图片
        var input = document.getElementById(e)
        if( input != null){
            input.setAttribute("type","text");
            input.setAttribute("type","file");
        }
        var tagTd = $("#" + srcValue + "td");
        tagTd.remove();
        layer.msg("删除成功！", {time: 500});
    });
}

function showBigImg(srcValue) {
    var img = $("#" + srcValue)[0].src;
    var imghtml = "<img src='" + img + "' style='max-width:100%;max-height: 100%' onclick='closeImg()' />";
    $("#showImg")[0].innerHTML = imghtml;
    document.getElementById("showImg").style.display = "block";
}

function showBigImg1(srcValue) {
    var img = $("#" + srcValue)[0].src;
    var imghtml = "<img src='" + img + "' style='max-width:100%;max-height: 100%' onclick='closeImg1()' />";
    $("#showImg1")[0].innerHTML = imghtml;
    document.getElementById("showImg1").style.display = "block";
}

function closeImg() {
    document.getElementById("showImg").style.display = "none";
}
function closeImg1() {
    document.getElementById("showImg1").style.display = "none";
}

/**
 * 至尊洗衣公用js
 */

// 查看大图
function toImage(url,layerWidth,layerHeight){

    var img_url = url;
    // 创建对象
    var img = new Image();
    // 改变图片的src
    img.src = img_url;
    // 判断是否有缓存
    var imgWidth=500;
    var imgHight=500;
    var initSize=500;
    var maxWidth="100%";
    var scrollWidth=0;
    if(img.complete){
        layerWidth=img.width;
        layerHeight=img.height;
        if(layerHeight>500&&layerWidth>500){
            if(layerHeight>=layerWidth){
                var zoomTimes=layerHeight/layerWidth;
                imgHight=(initSize*zoomTimes);
            }else{
                var zoomTimes=layerWidth/layerHeight;
                imgWidth=(initSize*zoomTimes);
                maxWidth="none";
            }
            toLayerImgWidthHeight(imgWidth,imgHight,initSize,initSize,url,maxWidth);
        }else{
            if(layerHeight<layerWidth){
                maxWidth="none";
            }
            if(layerHeight>500||layerWidth>500){
                if(layerHeight>=layerWidth){
                    var zoomTimes=layerWidth/layerHeight;
                    imgWidth=(initSize*zoomTimes);
                }else{
                    var zoomTimes=layerHeight/layerWidth;
                    imgHight=(initSize*zoomTimes);
                    maxWidth="none";
                }
                toLayerImgWidthHeight(imgWidth,imgHight,imgWidth,imgHight+1,url,maxWidth);
            }else{
                toLayerImgWidthHeight(layerWidth,layerHeight,layerWidth,layerHeight,url,maxWidth);
            }
        }
    }else{
        var index = layer.load(1, {
            shade: [0.1,'#000'] //0.1透明度的白色背景
        });
        // 加载完成执行
        img.onload = function(){
            layerWidth=img.width;
            layerHeight=img.height;
            if(layerHeight>initSize&&layerWidth>initSize){
                if(layerHeight>=layerWidth){
                    var zoomTimes=layerHeight/layerWidth;
                    imgHight=(initSize*zoomTimes);
                }else{
                    var zoomTimes=layerWidth/layerHeight;
                    imgWidth=(initSize*zoomTimes);
                    maxWidth="none";
                }
                layer.close(index);
                toLayerImgWidthHeight(imgWidth,imgHight,initSize,initSize,url,maxWidth);
            }else{
                layer.close(index);
                if(layerHeight<layerWidth){
                    maxWidth="none";
                }
                if(layerHeight>500||layerWidth>500){
                    if(layerHeight>=layerWidth){
                        var zoomTimes=layerWidth/layerHeight;
                        imgWidth=(initSize*zoomTimes);
                    }else{
                        var zoomTimes=layerHeight/layerWidth;
                        imgHight=(initSize*zoomTimes);
                        maxWidth="none";
                    }
                    toLayerImgWidthHeight(imgWidth,imgHight,imgWidth,imgHight+1,url,maxWidth);
                }else{
                    toLayerImgWidthHeight(layerWidth,layerHeight,layerWidth,layerHeight,url,maxWidth);
                }
            }
        };
    }
}

function toLayerImgWidthHeight(imgWidth,imgHeight,layerWidth,layerHeight,url,maxWidth){
    layer.open({
        type: 1,
        title: false,
        closeBtn: 2,
        area: [layerWidth+"px",layerHeight+"px"],
        scrollbar: false,
        skin: 'layui-layer-nobg', // 没有背景色
        shadeClose: true,
        content: '<img src="'+url+'" border="0" style="width:'+imgWidth+'px;height:'+imgHeight+'px;max-width:'+maxWidth+';">'
    });
}

//查看视频
function toVideo(url){
    layer.open({
        type: 1,
        title: '视频加载中……',
        area: ['450px','300px'],
        shade: 0.8,
        closeBtn: 2,
        shadeClose: true,
        scrollbar: false,
        content: "<video width='90%' height='83%' autoplay> <source src="+url+" type='video/mp4' > </video>",
        success: function(layero, index){
            layer.title('视频详情', index)
        }
    });
}
