<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp"%>
    <link href="../content/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" rel="stylesheet" />
    <style type="text/css">
        .margin-top-10 {
            margin-top: 10px;
        }
    </style>
</head>
<!-- END HEAD -->

<body>
<section class="content-header">
    <h1>
        Bootstrap Tags Input组件
        <small>控件面板<a href="http://timschlechter.github.io/bootstrap-tagsinput/examples/" target="_blank">官方文档</a></small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">UI</a></li>
        <li class="active">Bootstrap Tags Input</li>
    </ol>
</section>
<!-- Main content -->
<section class="content">
    <!-- BEGIN box-->
    <div class="box  bordered">
        <div class="box-body form">
            <!-- BEGIN FORM-->
            <form action="#" class="form-horizontal form-bordered ">
                <div class="form-body">

                    <div class="form-group">
                        <label class="control-label col-md-3">密码</label>
                        <div class="col-md-9">
                            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">邮箱</label>
                        <div class="col-md-9">
                            <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3">上传</label>
                        <div class="col-md-9">
                            <input type="file" id="exampleInputFile">
                            <p class="help-block">Example block-level help text here.</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3">日期</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control pull-right" id="datepicker">
                        </div>
                    </div>

                </div>
                <div class="form-actions">
                    <div class="row">
                        <div class="col-md-offset-3 col-md-9">
                            <button type="submit" class="btn btn-primary">Submit</button>
                            <button type="button" class="btn btn-success">
                                <i class="fa fa-check"></i> 确定
                            </button>
                            <button type="button" class="btn default"  onclick="reback()">取消</button>
                        </div>
                    </div>
                </div>
            </form>
            <!-- END FORM-->
        </div>
    </div>
</section>

<%@include file="/common/js_common.jsp"%>
<script src="${root}/content/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${root}/content/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${root}/content/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<!-- date-range-picker -->
<script src="${root}/content/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap datepicker -->
<script src="${root}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript">
$(function () {
    $('#datepicker').datepicker({
        autoclose: true
    });
})

function reback(){
    location.href = ("${root}/list");
}
</script>
</body>

</html>