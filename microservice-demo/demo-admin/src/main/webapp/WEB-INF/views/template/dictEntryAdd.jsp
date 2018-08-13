<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
    <script type="text/javascript" src="${root}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${root}/js/layer-v1.9.2/layer/layer.js"></script>
    <script src="${root}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
</head>
<body>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-8">
            <form id="form1">
                <div class="box-body">
                    <div class="form-group">
                    	<input type="hidden" class="form-control" id="dicttypeid" name="dicttypeid">
                        <label>字典项代码<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="dictid" name="dictid">
                        <input type="hidden" class="form-control" id="dicttype" name="dicttype" value="add">
                    </div>
                    <div class="form-group">
                        <label>字典项名称<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="dictname" name="dictname">
                    </div>
                     <div class="form-group">
                     	<label>状态<span style="color: red;">*</span></label>
                    		<select class="form-control" id="status" name="status">
	                                <option value="1" selected="selected">是</option>
	                                <option value="0" >否</option>
                        	</select>
                      </div>
                      <div class="form-group">
                        <label>排序<span style="color: red;">*</span></label>
                        <input type="text" class="form-control" id="sortno" name="sortno" maxlength="3">
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
var dicttypeid = parent.dicttypeid;
$("#dicttypeid").val(dicttypeid);

	var index = parent.layer.getFrameIndex(window.name);
    function reback(){
    	parent.layer.close(index);
    }
    //保存校验
    function checkForm(){
    	var dictid = $("#dictid").val();
		if(dictid == null || dictid == "" ){
			layer.alert("请填写字典项代码！");
			return false;
		}
    	var dictname = $("#dictname").val();
		if(dictname == null || dictname == "" ){
			layer.alert("请填写字典项名称！");
			return false;
		}
		var sortno = $("#sortno").val();
		if(sortno == null || sortno == "" ){
			layer.alert("请填写排序！");
			return false;
		}
		var g = /^[1-9]*[1-9][0-9]*$/;
		if(!g.test(sortno)){
			layer.alert("排序请填写数字！");
			return false;
		}
		return true;
	}
	function save(){
		var checkFlag = checkForm();
		if(!checkFlag){
			return false;
		}
		$.ajax({
			url:"${root}/dict/saveDictEntry",
			type:"post",
			data: $('form').serialize(),
			cache: false,
			async: false,
			success:function(text){
				var returnJson = text;
				var result = returnJson.isSuccess;
				var responseMsg = returnJson.responseMsg;
				if(result == true){
	            		parent.$('#returnValue').val("success");
		            	parent.layer.close(index);
	            }else if(returnJson.responseCode == 2202){
					layer.alert("记录已存在！");
				}else if(returnJson.responseCode == "error" ){
	            	nui.alert(responseMsg,"温馨提示");
	            }else{
	            	layer.alert("保存失败！");
	            }
			},
			error: function(jqXHR, textStatus, errorThrown){}
		});
	}
</script>
</body>
</html>
