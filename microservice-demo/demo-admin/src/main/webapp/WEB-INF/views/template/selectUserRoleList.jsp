<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<%
	String operatorId = request.getParameter("operatorId");
%>
<head>
    <%@include file="/common/meta.jsp"%>
	<script type="text/javascript" src="${root}/js/jquery-1.9.1.min.js"></script>
    <link href="${root}/content/min/css/supershopui.common.min.css" rel="stylesheet" />
    <link href="${root}/content/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
    <script type="text/javascript" src="${root}/js/layer-v1.9.2/layer/layer.js"></script>
    <!--全局通用框架样式 end-->
</head>
<body>
<section class="content">
	<div id="hiddenDiv">
		<input type="hidden" id="returnValue"> 
	</div>
    <div class="row">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="col-md-12">
        <div>
        	<button class="nui-button" style="width:60px;" iconCls="icon-save" id="btnSave" name="btnSave" onclick="saveUserRole()">保存</button>
        	<button class="nui-button" style="width:60px;" iconCls="icon-save" id="btnSave" name="btnSave" onclick="onCancel()">取消</button>
        </div>
            <!-- BEGIN SAMPLE TABLE PORTLET-->
            <input type="hidden" id="operatorId" value="<%=operatorId %>">
            <div class="box-body" style="padding-bottom:0px;">
                <div class="panel panel-default">
                <div class="table-scrollable">
                   	<table id="querylist" class="table table-striped"></table>
                </div>
            </div>

        </div>


    </div>

</section>
<%@include file="/common/js_common.jsp"%>

<script src="${root}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="${root}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
</body>
<script type="text/javascript">
var operatorId = document.getElementById("operatorId").value;
    $(function(){
	    $('#querylist').bootstrapTable({
	        columns: [ {  
                checkbox: true,
                formatter : stateFormatter
            },{
	            field: 'id',
	            title: '角色ID'
	        }, {
	            field: 'code',
	            title: '角色代码'
	        }, {
	            field: 'text',
	            title: '角色名称'
	        }],
	        clickToSelect : true,
	        sortName: 'id',
	        sortOrder: 'desc',
	        pagination: false,
	        sidePagination: 'server',
	        singleSelect : false,
	        cache:false,
	        method: 'post',
	        queryParams: function (params) {
	            return {
	                pageSize: params.limit,
	                offset: params.offset,
	                sortOrder: params.order
	            }
	        },
	        url : '${root}/userRole/getRoleList?operatorId='+operatorId
	    });
	
    });
   
    function onCancel(e) {
    	parent.$('#returnValue').val("");
    	parent.layer.close(index);
    }
    function stateFormatter(value, row, index) {
        if (row.isAutor == "1")
            return {
                disabled : false,//设置是否可用
                checked : true//设置选中
            };
        return value;
    }
    
	var index = parent.layer.getFrameIndex(window.name);
    //保存
    function saveUserRole(){
    	var checkRow = $('#querylist').bootstrapTable('getSelections');
    	var idArry=[];
		for(var i=0;i<checkRow.length;i++){
			var obj = checkRow[i].id+":"+checkRow[i].code;
			//obj.id = checkRow[i].id;
			//obj.code = checkRow[i].code;
			idArry.push(obj);
		}
		var jsonData = JSON.stringify(idArry);
    	layer.confirm('确认保存此角色授权？',
			function(){
				$.ajax({
					url:"${root}/userRole/updateUserRole?operatorId="+operatorId,
					type:"post",
					data:jsonData,
					cache: false,
					contentType:"application/json",
					success:function(text){
						if(text.responseCode== 0){
							 layer.msg("成功",{time:2000},function(){
								//layer.load();
								 parent.$('#returnValue').val("success");
					             parent.layer.close(index);
							}); 
						}else{
							layer.alert(text.responseMsg, "系统提示");
							roleGrid.unmask();
						}
					}
				});
			});
	}
    
    
	function grantMenu(){
		var checkRow = $('#querylist').bootstrapTable('getSelections');
		if(checkRow == null || checkRow.length == 0){
			layer.alert("请选中一条记录！");
			return false;
		}else if(checkRow.length == 1){
			pageii = layer.open({
				title : "菜单授权",
				type : 2,
				area : ["40%", "80%"],
				content : "${root}/role/toRoleMenuAdd?roleId="+checkRow[0].roleId,
				end : function(){
						var returnValue = $("#returnValue").val();
						if(returnValue == "success"){
							layer.msg("保存成功",{time:2000},function(){
								$("#returnValue").val("");
								var roleName = $('#roleName').val();
						    	var roleCode = $('#roleCode').val();
						    	var opt = {
						    		    url: "${root}/role/queryRoleInfo",
						    		    query:{
						    		      roleName: roleName,
						    		      roleCode: roleCode
						    		    }
						    		  };
					            $('#querylist').bootstrapTable('refresh',opt);
							});
						}
					}
				});
		}else{
			layer.alert("只能编辑一条记录！");
			return false;
		}
	}
	
</script>
</html>