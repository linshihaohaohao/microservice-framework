<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<%@page import="cn.com.bluemoon.admin.domain.vo.UserInfo" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp" %>
    <%
        UserInfo userInfo = new UserInfo();
        userInfo = (UserInfo) session.getAttribute("user");
        String userCode = userInfo.getAccount();
        String userName = userInfo.getRealName();
    %>
</head>
<body>
<section class="content">
    <div class="row">
        <div class="col-md-12">
            <form>
                <input type="hidden" id="opBy" name="opBy" value="<%=userCode%>">
                <input type="hidden" id="opName" name="opName" value="<%=userName%>">
                <input type="hidden" id="id" name="id">
                <div class="box-body">
                    <div class="panel-body">
                        <div class="col-md-12">
                            <div class="form-group form-horizontal">                            
                                <label class="control-label col-md-2">账号<span style="color: red;">*</span></label>
                                <div class="col-md-2">
                                    <input type="text" class="form-control" id="account"
                                           name="account" maxlength="20" readonly="readonly">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12" id="editDiv">
                            <div class="form-group form-horizontal">
                                <label class="control-label col-md-2">正文：<span style="color: red;">*</span></label>
                                <div class="checkbox col-md-10">
                                    <script id="editor" type="text/plain"></script>
                                    <input id="introduction" name="introduction" type="hidden">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<div class="form-actions">
    <div class="row">
        <div align="center">
            <button type="button" id="rebackBtn" class="btn btn-cancel" onclick="reback()">取消</button>
            <button type="button" id="saveBtn" class="btn btn-primary" onclick="save()"> 确定</button>
        </div>
    </div>
</div>
<%@include file="/common/js_common.jsp" %>
</body>
</html>
<link rel="stylesheet" href="${root}/content/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
<%--<link rel="stylesheet" href="${root}/content/plugins/timepicker/bootstrap-timepicker.min.css">--%>

<script type="text/javascript" src="${root}/js/webuploader/lrz.all.bundle.js"></script>
<%@include file="/common/uEditor.jsp"%>
<script>
    $(function () {
        debugger
        var columnCode = "";
        //获取帮助内容
        var updateData = parent.updateData;
        $("#id").val(updateData.id);
        $("#account").val(updateData.account);
      	//初始化编辑器
        ue.ready(function () {
            ue.setContent(updateData.introduction);
        });

    });

    var ue = UE.getEditor('editor');

    var index = parent.layer.getFrameIndex(window.name);

    function reback() {
        parent.layer.close(index);
    }

    function save() {
        var loadIndex = layer.load();
        $("#introduction").val(ue.getContent());

        var url = '${root}/managerApi/UserManager/update';
        $.ajax({
            type: "POST",
            url: url,
            data: $('form').serialize(),// 你的formid
            success: function (data) {
                if (data.isSuccess == true) {
                    parent.$('#returnValue').val("success");
                    parent.layer.close(index);
                } else {
                    layer.close(loadIndex);
                    layer.alert(data.msg);
                }
            }
        });
    }
</script>