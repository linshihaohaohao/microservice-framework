<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
    <script type="text/javascript" src="${root}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${root}/js/layer-v1.9.2/layer/layer.js"></script>
    <script src="${root}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${root}/js/nui.js"></script>
    <script src="${root}/js/jquery-1.9.1.min.js"></script>
    <%
	String roleId=request.getParameter("roleId");
%>
</head>
<body>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-8">
            <form id="form1">
                <div class="box-body">
                    <div class="form-group">
                        <label>角色代码<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="roleCode" name="roleCode">
                        <input type="hidden" class="form-control" id="oldRoleCode" name="oldRoleCode">
                    </div>
                    <div class="form-group">
                        <label>角色名称<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="roleName" name="roleName" 
                        maxlength="10" required="required">
                    </div>
                    <div class="form-group">
                        <label>角色描述<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="roleDesc" name="roleDesc" >
                        <input type="hidden" class="form-control" id="roleId" name="roleId" >
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<div class="form-actions">
    <div class="row">
        <div align="center">
            <button type="button" class="btn btn-cancel"  onclick="reback()">取消</button>
            <button type="button" class="btn btn-primary" onclick="save()"> 确定 </button>
        </div>
    </div>
</div>
<%@include file="/common/js_common.jsp"%>
<script type="text/javascript">

nui.parse();
var form1 = new nui.Form("#form1");
var oldRoleCode;
<%-- 验证角色是否存在 --%>
function checkRoleExist(roleCode){
	var data = {"roleCode":roleCode};
	var json = nui.encode(data);
	var isExist;
	$.ajax({
	url: "${root}/role/checkRoleExist",
	type: "POST",
	data: json,
	cache: false,
	async: false,
	contentType:"application/json",
	success: function(text){
		var returnJson = nui.decode(text);
		if(returnJson.isRoleExist == true){
			layer.alert(returnJson.responseMsg);
			isExist = true;
		}else{
			isExist = false;
		}
	},
	error: function(jqXHR, textStatus, errorThrown){}
	});
	return isExist;
}

	var index = parent.layer.getFrameIndex(window.name);
    function reback(){
    	parent.layer.close(index);
    }
    //保存校验
    function checkForm(){
    	var code = $("#roleCode").val();
    	var oldCode = $("#oldRoleCode").val();
		if(code == null || code == "" ){
			layer.alert("请填写角色代码！");
			return false;
		}
    	var name = $("#roleName").val();
		if(name == null || name == "" ){
			layer.alert("请填写名称名称！");
			return false;
		}
    	var position = $("#roleDesc").val();
    	/* var reg = /^[1-9]\d*$/;//正整数
		if(position == null || position == ""){
			layer.alert("请填写显示顺序！");
			return false;
		}else if(!reg.test(position)){
			layer.alert("显示顺序为正整数！");
			return false;
		} */
		if(oldCode != code){
			var ok = checkRoleExist(code);
			if(ok){
				return false;
			}
		}
		
		return true;
	}
	function save(){
		var checkFlag = checkForm();
		if(!checkFlag){
			return false;
		}
		var roleCode = $("#roleCode").val();
		var roleName = $("#roleName").val();
		var roleDesc = $("#roleDesc").val();
		var roleId = $('#roleId').val();
		var oldRoleCode = $('#oldRoleCode').val();
		var json = nui.encode({roleCode:roleCode,roleName:roleName,roleDesc:roleDesc,roleId:roleId,oldRoleCode:oldRoleCode});
		$.ajax({
			url:"${root}/role/updateRole",
			type:"post",
			dataType:"json",
			data:json,
			cache:false,
			contentType:"application/json",
			success:function(text){
				var returnJson = nui.decode(text);
				var result = returnJson.isSuccess;
				var responseMsg = returnJson.responseMsg;
				if(result == true){
	            		parent.$('#returnValue').val("success");
		            	parent.layer.close(index);
	            }else if(result == "error" ){
	            	nui.alert(responseMsg,"温馨提示");
	            }
			},
			error: function(jqXHR, textStatus, errorThrown){}
		});
	}
	
	<%-- 关闭窗口 --%>
	function CloseWindow(action){
		if(action=="close" && form1.isChanged()){
			if(confirm("数据已改变,是否先保存?")){
				return false;
			}
		}else if(window.CloseOwnerWindow){
			return window.CloseOwnerWindow(action);
		}else{
			return window.close();
		}
	}
</script>
</body>
</html>
