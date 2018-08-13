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
        <div class="col-xs-12">
            <form id="form1">
                <div class="panel-body">
                    <div class="form-group" style="margin-top:15px">
                        <label class="control-label col-xs-3">用户ID<span style="color: red;">*</span></label>
                         <div class="col-xs-6">
                        	<input type="text" class="form-control" id="userId" name="userId">
                        <input type="hidden" class="form-control" id="roleId" name="roleId">
                        <input type="hidden" class="form-control" id="staffNum" name="staffNum">
                        </div>
                        <div class="col-xs-3" style="text-align:left;">
                                    <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary" onclick="configUser()">查询</button>
                                </div>
                    </div>
                    </div>
                    <div class="form-group" style="margin-top:15px">
                    <div class="table-scrollable">
							<table class="table table-bordered table-striped table-hover"
								id="discounts">
								<tr>
									<td width='100px;' align='center'><strong>用户ID</strong></td>
									<td width='200px;' align='center'><strong>用户名称</strong></td>
									<td width='200px;' align='center'><strong>部门</strong></td>
								</tr>
							</table>
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

var index = parent.layer.getFrameIndex(window.name);
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
		var levelData = $("#discounts tr");
		if(levelData.length <= 1 ){
			layer.alert("人员信息不能为空");
			return false;
		}
		var roleId = $("#roleId").val();
		var staffNum = $("#staffNum").val();
		var jsonData = {roleId:roleId,staffNum:staffNum};
		$.ajax({
			url:"${root}/role/saveRoleUser",
			type:"post",
			data:jsonData,
			success:function(data){
				var json = JSON.parse(data);
				if(json.isSuccess){
					parent.$('#returnValue').val("success");
	            	parent.layer.close(index);
				}else{
					layer.msg(json.msg);
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
	
	function configUser() {
		var url = "${root}/role/getUserInfo";
		var userId = $("#userId").val();
		var jsonData = {userId : userId};
		$.ajax({
			type : "post",
			url : url,
			data : jsonData,// 你的formid
			success : function(data){
				var json = JSON.parse(data);
				if(json.isSuccess){
					var user = json.user;
					//$("#discounts tr").remove();
					var partItemIndex = $("#discounts tr").length;
					if(partItemIndex > 1){
						document.getElementById("discounts").deleteRow(1);	
					}
					setLevel(data);
				}else{
					layer.alert(json.msg);
				}
				//setLevel(levelJson);
			}
		});
	}
	
	function setLevel(levelJson) {
		var data = JSON.parse(levelJson);
		data = data.user;
		$("#staffNum").val(data.staffNum);
		var partItemIndex = $("#discounts tr").length;
		var newStr = "<tr>";
		newStr += "<td width='100px;' align='center'>"
				+ (data.staffNum) + "</td>";
		newStr += "<td width='200px;' align='center'>"+data.staffName+"</td>";
		newStr += "<td width='200px;' align='center'>"+data.department+"</td>";
		newStr += "</tr>";
		addTr("discounts", newStr);
	}
	
	function addTr(tab, trHtml) {
		//获取table最后一行 $("#tab tr:last")
		//获取table第一行 $("#tab tr").eq(0)
		//获取table倒数第二行 $("#tab tr").eq(-2)
		var $tr = $("#" + tab + " tr:last");
		if ($tr.size() == 0) {
			alert("指定的table id不存在！");
			return;
		}
		$tr.after(trHtml);
	}
</script>
</body>
</html>
