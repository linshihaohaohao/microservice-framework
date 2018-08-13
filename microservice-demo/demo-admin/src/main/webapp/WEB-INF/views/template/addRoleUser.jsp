<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%@page import="cn.com.bluemoon.admin.domain.vo.UserInfo"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
<title>配置角色人人员</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/js_common.jsp"%>
<link href="../content/min/css/supershopui.common.min.css"
	rel="stylesheet" />
<link href="../content/plugins/bootstrap-table/bootstrap-table.css"
	rel="stylesheet" />
<!--全局通用框架样式 end-->
 <%
	String roleId=request.getParameter("roleId");
%>
</head>
<body>
	<section class="content">
		<div id="hiddenDiv">
			<input type="hidden" id="roleId" value="<%=roleId%>">
			<input type="hidden" id="returnValue"> 
		</div>
		<div class="row">
			<!-- BEGIN SAMPLE TABLE PORTLET-->
			<div class="col-md-12">
				<!-- BEGIN SAMPLE TABLE PORTLET-->
				<div class="box-body" style="padding-bottom: 0px;">
					<div id="toolbar" class="btn-group">
						<button id="btn_add" type="button" class="btn btn-default"
							onclick="configUser()">
							<a> <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
								增加人员
							</a>
						</button>
						<button id="btn_edit" type="button" class="btn btn-default"
							onclick="del()">
							<a><span class="glyphicon glyphicon-remove"
								aria-hidden="true"></span>删除</a>
						</button>
					</div>
					<div class="table-scrollable">
						<table id="querylist" class="table table-striped"></table>
					</div>
				</div>

			</div>


		</div>

	</section>
	<script
		src="${root}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
	<script
		src="${root}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
</body>
<script>
	var roleId= "<%=roleId%>";
	
    $(function(){
    	$('#querylist').bootstrapTable({
	        columns: [{  
                checkbox: true  
            },{
	            field: 'userId',
	            title: '用户ID'
	        }, {
	            field: 'userName',
	            title: '用户名称'
	        }],
	        clickToSelect : true,
	        pagination: false,
	        sidePagination: 'server',
	        singleSelect : true,
	        cache:false,
	        queryParams: function (params) {
	            return {
	            	roleId: roleId
	            }
	        },
	        url : '${root}/role/getRoleUserList'
	    });
    });
    
    function refreshData(){
    	var opt = {url: "${root}/role/getRoleUserList"};
        $('#querylist').bootstrapTable('refresh',opt);
    }
   
    function configUser(){
    	var url = "${root}/role/toConfigUser";
		pageii = layer.open({
		title : "添加人员",
		type : 2,
		area : ["500px", "400px"],
		content : url,
		success : function(layero, index){
			var body = layer.getChildFrame('body', index);
			body.contents().find("#roleId").val(roleId);
		} ,
		end : function(){
				var returnValue = $("#returnValue").val();
				if(returnValue == "success"){
					layer.msg("保存成功",{time:2000},function(){
						$("#returnValue").val("");
				    	var opt = {
				    		    url: "${root}/role/getRoleUserList",
				    		    query:{
				    		    	roleId: roleId
				    		    }
				    		  }; 
			            $('#querylist').bootstrapTable('refresh',opt);
					});
				}
			}
		});
    }
    
	//删除
    function del(){
    	var checkRow = $('#querylist').bootstrapTable('getSelections');
    	if(checkRow.length == 0){
    		layer.alert("请选择需要删除的人员！");
    		return;
    	}
    	var jsonData = {userId : checkRow[0].userId , roleId : roleId};
    	layer.confirm("确认删除 <strong>"+checkRow[0].userName+"</strong>？",
			function(){
				var url = "${root}/role/delUserInfo";
				$.ajax({
					type : "post",
					url : url,
					data : jsonData,
					success : function(data){
						var jsonData = JSON.parse(data);
						if(jsonData.isSuccess){
							layer.msg("操作成功",{time:1500},function(){
								refreshData();
							});
						}else{
							layer.msg(jsonData.msg);
						}
					}
				});
			});
	}
    
</script>
</html>