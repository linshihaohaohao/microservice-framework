<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String parentNodeType=request.getParameter("menuType");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">   
    .nui-fit .mini-textbox-input{border:0;background:none;cursor:default;}
	.bg-div {
		width:170px;
		height:150px;
		margin-left:8px;
		margin-bottom:8px;
		float:left;
	}
	.bg-img {
		width:160px;
		height:150px;
		text-align:center;
	}
	.img-div{
		width:170px;
		height:160px;
		float:left;
		-moz-opacity: 0.5;
	}
	.delbtn:hover{
		display: "block";
	}
	.delbtn-div{
	    position:relative;
	    float:right;
	    filter:progid:DXImageTransform.Microsoft.Alpha(opacity=80);
	    -moz-opacity: 0.5;
	}
	.delbtn-div：hover{
		background:red;
		filter:progid:DXImageTransform.Microsoft.Alpha(opacity=100);
		-moz-opacity: 1;
	}
	#showImg{
		display:none;
		z-index:9999;
		width:450px;
		height:300px;
		position:absolute;
		left:50%;
		top:50%;
		margin-left:-325px;
		margin-top:-225px;
	}
</style>
</head>
<body>
<div style="padding-top:5px">
	<div id="form1" method="post">
        <table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table" >
           <tr class="odd">
                <th class="nui-form-label"><label for="appmenu.menuId$text">菜单id：</label></th>
                <td>    
                    <input id="appmenu.menuId" name="appmenu.menuId" class="nui-textbox nui-form-input nui-form-input" enabled="false"/>
                </td>
                <th class="nui-form-label"><label for="appmenu.menuCode$text">菜单代码：</label></th>
                <td>    
                    <input id="appmenu.menuCode" name="appmenu.menuCode" class="nui-textbox nui-form-input"
                     required="true" />
                </td>
            </tr>
            <tr>
                <th class="nui-form-label"><label for="appmenu.menuName$text">菜单名称：</label></th>
                <td>    
                    <input id="appmenu.menuName" name="appmenu.menuName" class="nui-textbox nui-form-input" required="true" />
                    <input id="appmenu.menuLevel" name="appmenu.menuLevel" class="nui-hidden"/>
                    <input id="appmenu.menuSeq" name="appmenu.menuSeq" class="nui-hidden"/>
                    <input id="appmenu.parentsId" name="appmenu.parentsId" class="nui-hidden"/>
                    <input id="appmenu.menuType" name="appmenu.menuType" class="nui-hidden"/>
                    <input id="appmenu.parentsCode" name="appmenu.parentsCode" class="nui-hidden"/>
                </td>
                <th class="nui-form-label"><label for="appmenu.menuLabel$text">菜单显示名称：</label></th>
                <td>    
                    <input id="appmenu.menuLabel" name="appmenu.menuLabel" class="nui-textbox nui-form-input" required="true"  vtype="rangeLength:1,20"/>
                </td>
            </tr>
            <tr>
                <th class="nui-form-label"><label for="appmenu.displayOrder$text">菜单显示顺序：</label></th>
                <td>    
                    <input id="appmenu.displayOrder" name="appmenu.displayOrder" class="nui-textbox nui-form-input" required="true"  vtype="range:0,100"/>
                </td>
            	<th class="nui-form-label"><label for="appmenu.ifleaf$text">是否应用功能：</label></th>
                <td>
                	<input id="appmenu.ifleaf" name="appmenu.ifleaf" class="nui-combobox  nui-form-input" required="true" 
                	onvaluechanged="leafChange"
                	 data="[{id:1,text:'是'},{id:0,text:'否'}]" />
                </td>
            </tr>
            <tr>
            	<th class="nui-form-label"><label for="appmenu.appCode$text">应用功能：</label></th>
                <td>
					<input emptyText="--请选择--" id="appmenu.appCode" name="appmenu.appCode"  class="nui-dictcombobox" 
					 onbeforeshowpopup="appcodeChange"
							dictTypeId="APPLICATION" required="true" textField="dictName" valueField="dictID"/>
				</td> 
            	<th class="nui-form-label"><label for="test1$text">功能描述：</label></th>
	            <td>
	                <input id="appmenu.functionDesc" name="appmenu.functionDesc"  class="nui-textbox nui-form-input" />
	            </td>
            </tr>
             <%if(parentNodeType.equals("pc")){ %>
            <tr class="pc">
            	<th class="nui-form-label"><label for="test1$text">页面路径：</label></th>
                <td colspan="3">
                	<input id="appmenu.functionPath" name="appmenu.functionPath" onvalidation="pagePathValid" class="nui-textbox nui-form-input" />
                </td>
            </tr>
             <%}%>
            <%if(parentNodeType.equals("app")){ %>
            <tr class="app">
            	<th class="nui-form-label"><label for="test1$text">app连接路径：</label></th>
                <td colspan="3">
                	<input id="appmenu.menuUrl" name="appmenu.menuUrl" class="nui-textbox nui-form-input" />
                </td>
            </tr>
            <tr class="app">
               <th class="nui-form-label" align="right" style='height:250px;'>app图标：</th>
				<td align="left" style="border-top:1px #cccccc solid;border-left:1px #cccccc solid;" valign="top">
					<span>
					<input class="nui-hidden" name="appmenu.iconImg" id="iconImg" /><!--照片路径 -->
					<input  id="file1" type="file" accept="image/*" onchange="fileChanged(this)"/>
					
					<span id="showpicture_iconImg" >
						<img class='icon-image' id="icon-image" 
						src='<%=request.getContextPath() %>/images/nopic.png' 
						style='width:170px;height:150px;'/>
					</span>
					</span>
				</td>
            </tr>
            <%}%>
        </table>
        <div class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;" borderStyle="border:0;">
	        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SaveData()">保存</a>
	    </div>        
    </div>
</div>
<script type="text/javascript">
        nui.parse();
        var form = new nui.Form("form1");
        var menuid = "<%= StringUtil.htmlFilter(request.getParameter("id")) %>";
        var menuType="<%= StringUtil.htmlFilter(request.getParameter("menuType")) %>";
        var ifleaf="<%= StringUtil.htmlFilter(request.getParameter("ifleaf")) %>";
        var json = nui.encode({menuid:menuid});
        var tempMenuCode = "";
        var oldMenuCode="";
        $.ajax({
                url: "<%=request.getContextPath()%>/menu/getMenuInfo",
                type: 'POST',
                data: json,
                cache: false,
                contentType:'application/json;charset=utf-8',
                success: function (text) {
                	var result={};
                	result.appmenu=text;//服务器返回的数据没有最外层
                    var o = nui.decode(result);
                    form.setData(result);
                    //设置图片地址
                    var relativePath=text.iconImg;
                    if(menuType=="app"){
                    	nui.get("appmenu.appCode").setValue("angel_app");
                    	nui.get("appmenu.appCode").disable();
                    }
                    if(text.ifleaf=="0"){
                    	nui.get("appmenu.functionPath").disable();
                    }
                    oldMenuCode=text.menuCode;
                    if(relativePath!=""&&relativePath!=null){
                    	//"http://tmallapi.bluemoon.com.cn/angelUpload/"+
                    	var src=relativePath;
                    	var userhtml=
    						"<a class='style-button' checkOnClick='true' groupName='style' style='margin-right:4px;'>"
    						+"<div class='img-div'><div class='delbtn-div'><img class='delbtn'  src='<%=request.getContextPath() %>/images/del.png' title='删除' onclick='deleteBg(\""+relativePath+"\")'/></div>"
    						+"<div class='bg-div'><img id='icon-image' class='bg-img' style='width:150px;height:130px;' src='"+src+"'/></div></div></a>";
                		$("span[id='showpicture_iconImg']").html(userhtml);
                    }
                }
            });
      //删除背景图
    	function deleteBg(srcValue){
    		nui.confirm("是否删除照片？","系统提示",
    		function(action){
    			if(action=="ok"){
    			   nui.get("iconImg").setValue("");
    			   var userhtml="<img class='icon-image' id='icon-image'"
    			   				+"src='<%=request.getContextPath() %>/images/nopic.png' style='width:170px;height:150px;'/>";
           			$("span[id='showpicture_iconImg']").html(userhtml);
           			SaveData("deleteImg");
    			}
            });
    	}
        function appcodeChange(e){
        	var datas=e.sender.data;
        	if(menuType=="pc"){
        		var length=datas.length;
        		var i=0;
        		for(i;i<length;i++){
        			var row=datas[i];
        			if(row.dictID=="angel_app"){
        				row.enabled=false;
        			}else{
        				row.enabled=true;
        			}
        		}
        	}
        }
        function leafChange(e){
        	var value=e.value;
        	if(value==0){
        		nui.get("appmenu.functionPath").setValue("#");
        		nui.get("appmenu.functionPath").disable();
        	}else{
        		nui.get("appmenu.functionPath").enable();
        		nui.get("appmenu.functionPath").setValue("");
        	}
        }
    	//路径必须以/开头
        function pagePathValid(e) {
        	var functionPath=nui.get("appmenu.functionPath").getValue();
        	if(functionPath==""){//如果没有填写路径 就不用验证，否则就得必填 而且/开头
        		return true;
        	}
        	var re=/\/+/;
            if (re.test(e.value)){
            	return true;
            }else{
            	 e.errorText = "路径必须以/开头";
                 e.isValid = false;
                 return false;
            }
        }
        function SaveData(op) {
        	//debugger
        	form.setChanged(false);
        	 var o = form.getData(true,true);
             var menu=o.appmenu;
             if(oldMenuCode!=menu.menuCode){
            	 codevalidation({"value":oldMenuCode});
             }
           	form.validate();
             if (form.isValid() == false) return;
             var menuType=menu.menuType;
             if("pc"==menuType){
             	var isleaf=menu.ifleaf;
             	var functionPath=menu.functionPath;
                 if(isleaf==1&&functionPath==""){
                		nui.alert("叶子菜单,请输入页面路径");
                		return;
                 }
             }
             var json = nui.encode(o);
              $.ajax({
                 url: "<%=request.getContextPath()%>/menu/updateMenu",
                 type: 'POST',
                 data: json,
                 cache: false,
                 contentType:'application/json; charset=UTF-8',
                 success: function (text) {
                	 if(op=="deleteImg"){
                		 nui.alert("删除图片成功");
                	 }else if(op=="updateImg"){
                		 nui.alert("更新图片成功");
                	 }else{
                		 nui.alert("更新成功");	 
                	 }
                     
                 },
                 error: function (jqXHR, textStatus, errorThrown) {
                	 nui.alert("更新失败");
                 }
             }); 
        }
        function fileChanged (e) {
         	lrz(e.files[0])
         	.then(function (rst) {
                 // 把处理的好的图片给用户看看呗
                 //alert(rst.base64);
                 var img = new Image();
                 img.src = rst.base64;
                 img.onload = function () {
                     //$("#icon-image").attr("src",rst.base64);
             		var srcValue="";
             		var userhtml=
 						"<a class='style-button' checkOnClick='true' groupName='style' style='margin-right:4px;'>"
 						+"<div class='img-div'><div class='delbtn-div'><img class='delbtn'  src='<%=request.getContextPath() %>/images/del.png' title='删除' onclick='deleteBg(\""+srcValue+"\")'/></div>"
 						+"<div class='bg-div'><img id='"+ srcValue +"' class='bg-img' style='width:150px;height:130px;margin-botton:4px;' src='"+rst.base64+"' onclick='showBigImg(\""+srcValue+"\")'/></div></div></a>";
             		$("span[id='showpicture_iconImg']").html(userhtml);
                 };
                 return rst;
             }).then(function (rst) {
             	var data={};
             	data.base64=rst.base64;
             	data.formData=rst.formData;
             	 $.ajax({
                      url: "<%=request.getContextPath()%>/menu/uploadImg",
                      data: nui.encode(data),
                      type: 'POST',
                      cache: false,
                      contentType:'application/json; charset=UTF-8',
                      success: function (data) {
                         // alert(JSON.stringify(data));
                          nui.get("iconImg").setValue(data);
                          SaveData("updateImg");
                      }
                  }); 
             });
         };
        //验证菜单编码是否存在
        function codevalidation(e){
        	var value=e.value;
        	var pattern=/^\w+$/;
            if (pattern.test(value)){
            	//return true;
            }else{
            	 e.errorText = "菜单编码必须合法";
                 e.isValid = false;
                 return false;
            }
        	if(e.isValid){
        		var data = {menuCode:e.value};
        		var json = nui.encode(data);
	        	$.ajax({
                    url: "<%=request.getContextPath()%>/menu/checkMenuCode",
                    type: 'POST',
	                data: json,
	                cache: false,
	                contentType:'application/json; charset=UTF-8',
                    async:false,
                    success: function (text) {
                        if(text){
                        	e.errorText = "菜单代码已存在!";
	        				e.isValid = false;
                        }
                    }
	           });
        	}
        }
    </script>
    <script src="<%=contextPath%>/js/lrz.bundle.js"></script>
</body>
</html>
