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
</head>
<body>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-8">
            <form id="form1">
                <div class="box-body">
                    <div class="form-group">
                        <label>类型代码<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="dicttypeid" name="dicttypeid">
                    </div>
                    <div class="form-group">
                        <label>类型名称<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="dicttypename" name="dicttypename" 
                        maxlength="10" required="required">
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
    	var code = $("#dicttypeid").val();
		if(code == null || code == "" ){
			layer.alert("请填写类型代码！");
			return false;
		}
    	var name = $("#dicttypename").val();
		if(name == null || name == "" ){
			layer.alert("请填写类型名称！");
			return false;
		}
		return true;
	}
	function save(){
		var checkFlag = checkForm();
		if(!checkFlag){
			return false;
		}
		var dicttypeid = $("#dicttypeid").val();
		var dicttypename = $("#dicttypename").val();
		var type = "add";
		var json = nui.encode({dicttypeid:dicttypeid,dicttypename:dicttypename,type:type});
		$.ajax({
			url:"${root}/dict/saveDictType",
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
	            }else if(returnJson.responseCode == 2202){
					nui.alert("记录已存在！");
				}else if(returnJson.responseCode == "error" ){
	            	nui.alert(responseMsg,"温馨提示");
	            }else{
	            	nui.alert("保存失败！");
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
