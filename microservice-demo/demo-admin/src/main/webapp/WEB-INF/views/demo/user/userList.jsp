<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>用户管理列表</title>
    <%@include file="/common/meta.jsp" %>
    <link href="${root}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
    <link href="${root}/content/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet"/>
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
                                <label class="control-label col-sm-1" for="account">账号：</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="account">
                                </div>
                            </div>

                            <div class="col-sm-3" style="text-align:left;">
                                <button type="button" style="margin-left:50px" id="btn_query"
                                        class="btn btn-primary">查询
                                </button>
                                <button type="button" style="margin-left:50px" id="btn_reset" class="btn">重置
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <div id="toolbar" class="btn-group">
                    <button id="btn_edit" type="button" class="btn btn-default" onclick="view()">
                        <a>
                            <span class="fa fa-pencil-square-o" aria-hidden="true"></span>编辑简介
                        </a>
                    </button>
                </div>
                <div class="table-scrollable">
                    <table id="querylist" class="table table-striped"></table>
                </div>
            </div>

        </div>


    </div>

</section>
<%@include file="/common/js_common.jsp" %>

<script src="${root}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="${root}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
</body>
<script>
    $(function () {
        $('#querylist').bootstrapTable({
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: 'ID'
            }, {
                field: 'account',
                title: '账号'
            }, {
                field: 'signInTime',
                title: '注册时间',
                formatter: dateFormat
            }, {
                field: 'introduction',
                title: '个人简介'
            }],
            clickToSelect: true,
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            sidePagination: 'server',
            singleSelect: true,
            pageNumber: 1,
            pageSize: 50,
            pageList: [10, 50, 100],
            cache: false,
            queryParams: function (params) {
                var account = $("#account").val();
                return {
                	account: account,
                    pageSize: params.limit,
                    offset: params.offset,
                    sortOrder: params.order
                }
            },
            url: '${root}/managerApi/UserManager/list'
        });

        $('#btn_query').click(function () {
            var account = $("#account").val();
            var opt = {
                url: "${root}/managerApi/UserManager/list",
                query: {
                	account: account
                }
            };
            $('#querylist').bootstrapTable('refresh', opt);
        });

        $("#btn_reset").click(function () {
            $("#account").val("");
        });

        function dateFormat(value) {
            try {
                var k = parseInt(value["time"]);
                if (k < 0)
                    return null;

                var date = new Date(value["time"]);
                var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
                var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
//            var milliseconds = date.getMilliseconds();
                return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
            }
            catch (ex) {
                return "";
            }
        }
    });

    var updateData;

    function view() {
        var checkRow = $('#querylist').bootstrapTable('getSelections');
        if (checkRow.length == 0) {
            layer.alert("请选择需要编辑的数据！");
            return;
        }
        //查看
        updateData = checkRow[0];
        pageii = layer.open({
            title: "编辑简介",
            type: 2,
            area: ["90%", "80%"],
            content: "${root}/demoUser/update"
        });
    }


</script>
</html>