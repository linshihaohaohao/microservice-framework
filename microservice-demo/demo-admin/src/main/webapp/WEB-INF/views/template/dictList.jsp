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
                    <div class="panel-heading">查询条件</div>
                    <div class="panel-body">
                        <form id="formSearch" class="form-horizontal">
                            <div class="form-group" style="margin-top:15px">
                                <label class="control-label col-sm-1" for="txt_search_departmentname">类型代码</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="dicttypeid">
                                </div>
                                <label class="control-label col-sm-1" for="txt_search_departmentname">类型名称</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="dicttypename">
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
                    <button id="btn_add" type="button" class="btn btn-default" onclick="add();">
                        <a  href="#"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增 </a>
                    </button>
                    <button id="btn_edit" type="button" class="btn btn-default"  onclick="javascript:update();">
                        <a  href="#"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>
                    </button>
                    <button id="btn_delete" type="button" class="btn btn-default" onclick="javascript:del();">
                        <a href="#"> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>
                    </button>
                     <button id="btn_edit" type="button" class="btn btn-default" onclick="javascript:grantMenu();">
                        <a  href="#"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>配置业务字典项</a>
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
	            field: 'dicttypeid',
	            title: '类型代码'
	        }, {
	            field: 'dicttypename',
	            title: '类型名称'
	        }],
	        clickToSelect : true,
	        sortName: 'dicttypeid',
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
	                sortOrder: params.order,
	                sortName: this.sortName,
	                sortOrder: this.sortOrder
	            }
	        },
	        url : '${root}/dict/getDictTypeList'
	    });
	
// 	        url: '${root}/getItemCategory'
	    $('#btn_query').click(function () {
	    	var dicttypeid = $('#dicttypeid').val();
	    	var dicttypename = $('#dicttypename').val();
	    	var opt = {
	    		    url: "${root}/dict/getDictTypeList",
	    		    query:{
	    		    	dicttypeid: dicttypeid,
	    		    	dicttypename: dicttypename
	    		    }
	    		  };
            $('#querylist').bootstrapTable('refresh',opt);
	    });
	    
	    $("#btn_reset").click(function(){
	    	$('#dicttypeid').val("");
	    	$('#dicttypename').val("");
	    });
	    
    });
   
    //新增
	function add(){
		var url = "${root}/dict/dictTypeAdd";
		pageii = layer.open({
		title : "新增字典类型",
		type : 2,
		area : ["500px", "400px"],
		content : url,
		end : function(){
				var returnValue = $("#returnValue").val();
				if(returnValue == "success"){
					layer.msg("保存成功",{time:2000},function(){
						$("#returnValue").val("");
						var dicttypeid = $('#dicttypeid').val();
				    	var dicttypename = $('#dicttypename').val();
				    	var opt = {
				    		    url: "${root}/dict/getDictTypeList",
				    		    query:{
				    		    	dicttypeid: dicttypeid,
				    		    	dicttypename: dicttypename
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
    	var url = "${root}/dict/toDictTypeUpdate";
		pageii = layer.open({
		title : "修改字典类型",
		type : 2,
		area : ["500px", "400px"],
		content : url,
		success: function (layero, index) { 
			var body = layer.getChildFrame('body', index);
			body.contents().find("#dicttypeid").val(updateData.dicttypeid);
			body.contents().find("#dicttypename").val(updateData.dicttypename);
		},
		end : function(){
				debugger;
				var returnValue = $("#returnValue").val();
				if(returnValue == "success"){
					layer.msg("保存成功",{time:2000},function(){
						$("#returnValue").val("");
						var dicttypeid = $('#dicttypeid').val();
				    	var dicttypename = $('#dicttypename').val();
				    	var opt = {
				    		    url: "${root}/dict/getDictTypeList",
				    		    query:{
				    		    	dicttypeid: dicttypeid,
				    		    	dicttypename: dicttypename
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
    		layer.alert("请选择需要删除的字典类型！");
    		return;
    	}
    	var dicttypeid=checkRow[0].dicttypeid;
		var json = nui.encode({DICTTYPEID:dicttypeid});
    	layer.confirm('所有关联的业务字典类型和业务字典项都将被删除，确认删除业务字典类型？',
			function(){
				$.ajax({
					url:"${root}/dict/deleteDictType",
					type:"post",
					data:json,
					cache: false,
					contentType:"application/json",
					success:function(text){
						if(text.responseCode== 0){
							layer.msg("删除成功",{time:2000},function(){
								//layer.load();
								var dicttypeid = $('#dicttypeid').val();
						    	var dicttypename = $('#dicttypename').val();
						    	var opt = {
						    		    url: "${root}/dict/getDictTypeList",
						    		    query:{
						    		    	dicttypeid: dicttypeid,
						    		      	dicttypename: dicttypename
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
    
    //配置业务字典项
    function grantMenu(){
    	//TODO 
    	var checkRow = $('#querylist').bootstrapTable('getSelections');
    	if(checkRow.length == 0){
    		layer.alert("请先选择一条业务字典项记录！");
    		return;
    	}
    	updateData = checkRow[0];
    	var url = "${root}/dict/toDictEntryList";
		pageii = layer.open({
		title : "配置业务字典项",
		type : 2,
		area : ["70%", "70%"],
		content : url,
		success: function (layero, index) { 
			var body = layer.getChildFrame('body', index);
			body.contents().find("#dicttypeid").val(updateData.dicttypeid);
		},
		end : function(){
				debugger;
				var returnValue = $("#returnValue").val();
				if(returnValue == "success"){
					layer.msg("保存成功",{time:2000},function(){
						var dicttypeid = $('#dicttypeid').val();
				    	var dicttypename = $('#dicttypename').val();
				    	var opt = {
				    		    url: "${root}/dict/getDictTypeList",
				    		    query:{
				    		    	dicttypeid: dicttypeid,
				    		    	dicttypename: dicttypename
				    		    }
				    		  };
			            $('#querylist').bootstrapTable('refresh',opt);
					});
				}
			}
		});
    }
	
</script>
</html>