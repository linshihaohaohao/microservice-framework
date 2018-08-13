<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

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
            <!-- BEGIN SAMPLE TABLE PORTLET-->
            <div class="box-body" style="padding-bottom:0px;">
                <div class="panel panel-default">
                    <div class="panel-heading">查询条件</div>
                    <div class="panel-body">
                        <form id="formSearch" class="form-horizontal">
                            <div class="form-group" style="margin-top:15px">
                                <label class="control-label col-sm-1" for="txt_search_departmentname">角色代码</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="roleCode">
                                </div>
                                <label class="control-label col-sm-1" for="txt_search_departmentname">角色名称</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="roleName">
                                </div>
                                <div class="col-sm-4" style="text-align:left;">
                                    <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">查询</button>
                                    <button type="button" style="margin-left:50px" id="btn_reset" class="btn">重置</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div id="toolbar" class="btn-group">
                    <button id="btn_add" type="button" class="btn btn-default">
                        <a  href="#" onclick="add();"> <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增 </a>
                    </button>
                    <button id="btn_edit" type="button" class="btn btn-default">
                        <a  href="#" onclick="javascript:update();"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>
                    </button>
                    <button id="btn_delete" type="button" class="btn btn-default">
                        <a  href="#" onclick="javascript:del();"> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>
                    </button>
                     <button id="btn_edit" type="button" class="btn btn-default">
                        <a  href="#" onclick="javascript:grantMenu();"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>配置菜单权限</a>
                    </button>
                    <button id="btn_edit" type="button" class="btn btn-default" onclick="grantUser()">
                        <a><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>配置人员</a>
                    </button>
                </div>
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
<script>
    $(function(){
	    $('#querylist').bootstrapTable({
	        columns: [ {  
                checkbox: true  
            },{
	            field: 'roleId',
	            title: '角色ID'
	        }, {
	            field: 'roleCode',
	            title: '角色代码'
	        }, {
	            field: 'roleName',
	            title: '角色名称'
	        },{
	            field: 'roleDesc',
	            title: '角色描述'
	        }],
	        clickToSelect : true,
	        sortName: 'id',
	        sortOrder: 'desc',
	        pagination: true,
	        sidePagination: 'server',
	        singleSelect : true,
	        pageNumber: 1,
	        pageSize: 10,
	        pageList: [10, 20, 30],
	        cache:false,
	        method: 'post',
	        queryParams: function (params) {
	            return {
	                pageSize: params.limit,
	                offset: params.offset,
	                sortOrder: params.order
	            }
	        },
	        url : '${root}/role/queryRoleInfo'
	    });
	
// 	        url: '${root}/getItemCategory'
	    $('#btn_query').click(function () {
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
	    
	    $("#btn_reset").click(function(){
	    	$('#roleName').val("");
	    	$('#roleCode').val("");
	    });
	    
    });
   
    //新增
	function add(){
		var url = "${root}/role/roleAdd";
		pageii = layer.open({
		title : "新增角色",
		type : 2,
		area : ["500px", "400px"],
		content : url,
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
	}
    
    var updateData;
    //编辑
    function update(){
    	//TODO 
    	var checkRow = $('#querylist').bootstrapTable('getSelections');
    	if(checkRow.length == 0){
    		layer.alert("请选择需要编辑的角色！");
    		return;
    	}
    	updateData = checkRow[0];
    	var url = "${root}/role/toRoleUpdate";
		pageii = layer.open({
		title : "编辑角色",
		type : 2,
		area : ["500px", "400px"],
		content : url,
		success: function (layero, index) { 
			var body = layer.getChildFrame('body', index);
			body.contents().find("#roleCode").val(updateData.roleCode);
			body.contents().find("#oldRoleCode").val(updateData.roleCode);
			body.contents().find("#roleName").val(updateData.roleName);  
			body.contents().find("#roleDesc").val(updateData.roleDesc);
			body.contents().find("#roleId").val(updateData.roleId);
		},
		end : function(){
				debugger;
				var returnValue = $("#returnValue").val();
				if(returnValue == "success"){
					layer.msg("更新成功",{time:2000},function(){
						$("#returnValue").val("");
						//layer.load();
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
    }
    
    //删除
    function del(){
    	var checkRow = $('#querylist').bootstrapTable('getSelections');
    	if(checkRow.length == 0){
    		layer.alert("请选择需要编辑的类目！");
    		return;
    	}
    	var idArry=[];
		for(var i=0;i<checkRow.length;i++){
			idArry.push(checkRow[i].roleId);
		}
		var jsonData = JSON.stringify(idArry);
    	layer.confirm('确认删除此角色？',
			function(){
				$.ajax({
					url:"${root}/role/delRole",
					type:"post",
					data:jsonData,
					cache: false,
					contentType:"application/json",
					success:function(text){
						if(text.responseCode== 0){
							layer.msg("删除成功",{time:2000},function(){
								//layer.load();
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
	
	function grantUser(){
		var checkRow = $('#querylist').bootstrapTable('getSelections');
		if(checkRow == null || checkRow.length == 0){
			layer.alert("请选中一条记录！");
			return false;
		}else if(checkRow.length == 1){
			pageii = layer.open({
				title : "配置用户",
				type : 2,
				area : ["60%", "80%"],
				content : "${root}/role/toRoleUserAdd?roleId="+checkRow[0].roleId,
				end : function(){
						var returnValue = $("#returnValue").val();
						if(returnValue == "success"){
							layer.msg("保存成功",{time:2000},function(){
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