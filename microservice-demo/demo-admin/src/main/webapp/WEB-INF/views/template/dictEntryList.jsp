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
    <script type="text/javascript" src="${root}/js/nui.js"></script>
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
            <div class="panel-body">
             <div class="form-group" style="margin-top:15px">
                        <label class="control-label col-sm-1" for="txt_search_departmentname">类型代码</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="dicttypeid" readonly="readonly">
                        </div>
              </div>
              </div>
              </div>
                <div id="toolbar" class="btn-group">
                 
                    <button id="btn_add" type="button" class="btn btn-default" onclick="add();">
                        <a  href="#"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 添加 </a>
                    </button>
                    <button id="btn_edit" type="button" class="btn btn-default"  onclick="javascript:update();">
                        <a  href="#"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>
                    </button>
                    <button id="btn_delete" type="button" class="btn btn-default" onclick="javascript:del();">
                        <a href="#"> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>
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
	var updateData = parent.updateData;
	var dicttypeid = updateData.dicttypeid;
    $(function(){
    	res();
    });
    function res(){

	    $('#querylist').bootstrapTable({
	        columns: [ {  
                checkbox: true  
            },{
	            field: 'dicttypeid',
	            title: '字典类型代码',
	        },{
	            field: 'dictid',
	            title: '字典项代码'
	        }, {
	            field: 'dictname',
	            title: '字典项名称'
	        }, {
	            field: 'status',
	            title: '字典项状态'
	        }, {
	            field: 'sortno',
	            title: '排序'
	        }],
	        clickToSelect : true,
	        sortName: 'sortno',
	        sortOrder: 'asc',
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
	                sortOrder: params.order,
	                sortName: this.sortName,
	                sortOrder: this.sortOrder,
	                dicttypeid : dicttypeid
	            }
	        },
	        url : '${root}/dict/getDictEntryList',
	        onLoadSuccess :function(data){
	        	var result = data["rows"];
	        	$.each(result,function(index,content){
	        		if(content["status"] == "1"){
	        			content["status"] = "有效";
	        		}else{
	        			content["status"] = "无效";
	        		}
                });
	        	$("#querylist").bootstrapTable("load",data);
	        }
	    });
	    $('#querylist').bootstrapTable('hideColumn', 'dicttypeid');
    }
    
    //新增
	function add(){
		var url = "${root}/dict/dictEntryAdd";
		pageii = layer.open({
		title : "添加业务字典项",
		type : 2,
		area : ["500px", "400px"],
		content : url,
		end : function(){
				var returnValue = $("#returnValue").val();
				if(returnValue == "success"){
					layer.msg("保存成功",{time:2000},function(){
					$("#returnValue").val("");
				    	var opt = {
				    		    url: "${root}/dict/getDictEntryList",
				    		    query:{
				    		    	dicttypeid: dicttypeid
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
    		layer.alert("请先选择一条业务字典项记录！");
    		return;
    	}
    	updateData = checkRow[0];
    	var url = "${root}/dict/toDictEntryUpdate";
		pageii = layer.open({
		title : "修改字典类型",
		type : 2,
		area : ["500px", "400px"],
		content : url,
		success: function (layero, index) { 
			var body = layer.getChildFrame('body', index);
			var status = updateData.status;
			if(status == "有效"){
				status = "1";
			}else{
				status = "0";
			}
			body.contents().find("#dicttypeid").val(updateData.dicttypeid);
			body.contents().find("#dictid").val(updateData.dictid);
			body.contents().find("#dictname").val(updateData.dictname);
			body.contents().find("#sortno").val(updateData.sortno);
			body.contents().find("#status").val(status);
		},
		end : function(){
				var returnValue = $("#returnValue").val();
				if(returnValue == "success"){
					layer.msg("保存成功",{time:2000},function(){
					$("#returnValue").val("");
				    	var opt = {
				    		    url: "${root}/dict/getDictEntryList",
				    		    query:{
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
    		layer.alert("请选择需要删除的字典项！");
    		return;
    	}
    	var dictid=checkRow[0].dictid;
		var json = nui.encode({DICTTYPEID:dicttypeid,dictid:dictid});
    	layer.confirm('所有关联的业务字典项都将被删除，确认删除业务字典项？',
			function(){
				$.ajax({
					url:"${root}/dict/deleteDictEntry",
					type:"post",
					data:json,
					cache: false,
					contentType:"application/json",
					success:function(text){
						if(text.responseCode== 0){
							layer.msg("删除成功",{time:2000},function(){
								//layer.load();
						    	var opt = {
						    		    url: "${root}/dict/getDictEntryList",
						    		    query:{
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