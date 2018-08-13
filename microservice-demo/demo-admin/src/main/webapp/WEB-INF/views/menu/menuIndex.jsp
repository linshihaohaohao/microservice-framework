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

    <link href="${root}/content/plugins/jqgrid/jqgrid.css" rel="stylesheet"/>


    <script src="${root}/content/plugins/datepicker/WdatePicker.js"></script>

    <link href="${root}/content/plugins/wdtree/tree.css" rel="stylesheet"/>
    <style>
        html,body {
            overflow: hidden;
        }
    </style>
</head>
<body>
<section class="content-header">
    <h1>
        菜单管理
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">菜单模块管理</a></li>
    </ol>
</section>
<!-- Main content -->
<section class="content">
    <div class="ui-layout" id="layout" style="height: 100%; width: 100%;">
        <div class="ui-layout-west">
            <div class="west-Panel">
                <div class="panel-Title">功能目录</div>
                <div id="itemTree"></div>
            </div>
        </div>
        <div class="ui-layout-center">
            <div class="center-Panel">
                <div class="panel-Title">功能信息</div>
                <div class="titlePanel">
                    <div class="title-search">
                        <table>
                            <tr>
                                <td>
                                    <div id="queryCondition" class="btn-group">
                                        <a class="btn btn-default dropdown-text" data-toggle="dropdown">选择条件</a>
                                        <a class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></a>
                                        <ul class="dropdown-menu">
                                            <li><a data-value="menuCode">菜单编码</a></li>
                                            <li><a data-value="menuLabel">菜单显示的名称</a></li>
                                        </ul>
                                    </div>
                                </td>
                                <td style="padding-left: 2px;">
                                    <input id="txt_Keyword" type="text" class="form-control" placeholder="请输入要查询关键字" style="width: 200px;"/>
                                </td>
                                <td style="padding-left: 5px;">
                                    <a id="btn_Search" class="btn btn-primary"><i class="fa fa-search lr-search"></i>&nbsp;查询</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="toolbar moduletoolbar">
                        <div class="btn-group">
                            <a class="btn btn-default lr-replace"><i class="fa fa-refresh"></i>&nbsp;刷新</a>
                            <a class="btn btn-default lr-add"><i class="fa fa-plus"></i>&nbsp;新增</a>
                            <a class="btn btn-default lr-edit"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>
                            <a class="btn btn-default lr-delete"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
                        </div>
                        <script></script>
                    </div>
                </div>
                <div class="gridPanel">
                    <table id="gridTable"></table>
                </div>
            </div>
        </div>
    </div>
</section>


</body>
</html>
<%@include file="/common/js_common.jsp"%>

<script src="${root}/content/plugins/jqgrid/grid.locale-cn.js"></script>
<script src="${root}/content/plugins/jqgrid/jqgrid.js"></script>
<script src="${root}/content/supermgr/Base/js/common.js"></script>

<script src="${root}/content/plugins/validator/validator.js"></script>
<script src="${root}/content/plugins/layout/jquery.layout.js"></script>

<script src="${root}/content/plugins/wdtree/tree.js"></script>
<script src="${root}/js/menu/menu.js"></script>
<script type="text/javascript">
    var module = new ModuleMgr("${root}");
    module.initGridPage();
    module.loadTree();
    module.loadGrid();
    $(window).resize();
</script>