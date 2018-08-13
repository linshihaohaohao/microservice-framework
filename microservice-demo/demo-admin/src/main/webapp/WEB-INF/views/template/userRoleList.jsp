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
                                <label class="control-label col-sm-1" for="txt_search_departmentname">用户ID</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="operatorId">
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
                     <button id="btn_edit" type="button" class="btn btn-default">
                        <a  href="#" onclick="javascript:grantMenu();"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>配置用户角色</a>
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
	            field: 'staffNum',
	            title: '用户ID'
	        }, {
	            field: 'staffName',
	            title: '用户名称'
	        }, {
	            field: 'postState',
	            title: '在职状态'
	        }],
	        clickToSelect : true,
	        sidePagination: 'server',
	        singleSelect : true,
	        cache:false,
	        method: 'post',
	        queryParams: function (params) {
	            return {
	            }
	        },
	        url : '${root}/criteria/query',
	        onLoadSuccess :function(data){
	        	var result = data["rows"];
	        	$.each(result,function(index,content){
	        		if(content["postState"] == "3"){
	        			content["postState"] = "正常";
	        		}else{
	        			content["postState"] = "失效";
	        		}
                });
	        	$("#querylist").bootstrapTable("load",data);
	        }
	    });
	
// 	        url: '${root}/getItemCategory'
	    $('#btn_query').click(function () {
	    	var operatorId = $('#operatorId').val();
	    	var opt = {
	    		    url: "${root}/criteria/query",
	    		    query:{
	    		      operatorId: operatorId
	    		    }
	    		  };
            $('#querylist').bootstrapTable('refresh',opt);
	    });
	    
	    $("#btn_reset").click(function(){
	    	$('#operatorId').val("");
	    	$('#userName').val("");
	    });
	    
    });
   
	function grantMenu(){
		var checkRow = $('#querylist').bootstrapTable('getSelections');
		if(checkRow == null || checkRow.length == 0){
			layer.alert("请选中一条记录！");
			return false;
		}else if(checkRow.length == 1){
			var user = checkRow[0];
			if("正常" != user.postState){
				layer.alert("用户状态已失效，请重新选择！");
				return false;
			}
			pageii = layer.open({
				title : "角色授权",
				type : 2,
				area : ["50%", "80%"],
				content : "${root}/userRole/toSelectUserRoleList?operatorId="+user.staffNum,
				end : function(){
					}
				});
		}else{
			layer.alert("只能配置一名用户！");
			return false;
		}
	}
</script>
</html>