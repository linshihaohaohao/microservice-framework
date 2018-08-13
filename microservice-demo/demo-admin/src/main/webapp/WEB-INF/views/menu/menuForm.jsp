<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <%@include file="/common/meta.jsp" %>
    <meta charset="utf-8"/>
    <link href="${root}/content/supermgr/Base/css/common.css" rel="stylesheet"/>

    <link href="${root}/content/plugins/jqgrid/jqgrid.css" rel="stylesheet"/>
    <link href="${root}/content/plugins/wizard/wizard.css" rel="stylesheet"/>
    <link href="${root}/content/plugins/wdtree/tree.css" rel="stylesheet"/>

    <script src="${root}/content/plugins/datepicker/WdatePicker.js"></script>
    <style type="text/css">
        .step-content {
            min-height: 320px !important;
        }
    </style>
</head>
<body>
<form id="form1">

    <div class="formDiv">
        <div class="widget-body">
            <div id="wizard" class="wizard" data-target="#wizard-steps"
                 style="border-left: none; border-top: none; border-right: none;">
                <ul class="steps">
                    <li data-target="#step-1" class="active"><span class="step">1</span>系统功能<span
                            class="chevron"></span></li>
                    <%--<li data-target="#step-2"><span class="step">2</span>系统按钮<span class="chevron"></span></li>--%>
                </ul>
            </div>
            <div class="step-content" id="wizard-steps"
                 style="border-left: none; border-bottom: none; border-right: none;">
                <div class="step-pane active" id="step-1"
                     style="margin-left: 0px; margin-top: 15px; margin-right: 30px;">
                    <input id="menuId" type="hidden"/>
                    <table class="form">
                        <tr>
                            <th class="formTitle">菜单编码<font face="宋体">*</font></th>
                            <td class="formValue">
                                <input id="menuCode" name="menuCode" type="text" class="form-control"
                                       placeholder="请输入菜单编码" isvalid="yes" checkexpession="NotNull"/>
                            </td>
                            <th class="formTitle">菜单名称<font face="宋体">*</font></th>
                            <td class="formValue">
                                <input id="menuName" name="menuName" type="text" class="form-control"
                                       placeholder="请输入菜单名称" isvalid="yes" checkexpession="NotNull"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="formTitle">上级</th>
                            <td class="formValue">
                                <div id="parentId" type="selectTree" class="ui-select">
                                </div>
                            </td>

                            <th class="formTitle">菜单排序<font face="宋体">*</font></th>
                            <td class="formValue">
                                <input id="displayOrder" name="displayOrder" type="text" class="form-control"
                                       placeholder="请输入菜单排序" isvalid="yes" checkexpession="Num"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="formTitle">菜单显示的名称<font face="宋体">*</font></th>
                            <td class="formValue">
                                <input id="menuLabel" name="menuLabel" type="text" class="form-control"
                                       placeholder="请输入菜单显示的名称" isvalid="yes" checkexpession="NotNull"/>
                            </td>
                            <th class="formTitle">菜单功能路径<font face="宋体">*</font></th>
                            <td class="formValue">
                                <input id="functionPath" name="functionPath" type="text" class="form-control"
                                       placeholder="菜单功能路径" isvalid="yes" checkexpession="NotNull"/>
                            </td>
                        </tr>

                        <tr>
                            <th class="formTitle" valign="top" style="padding-top: 4px;">
                                功能描述
                            </th>
                            <td class="formValue" colspan="3">
                                <textarea id="functionDesc" name="functionDesc" class="form-control"
                                          style="height: 70px;"></textarea>
                            </td>
                        </tr>
                    </table>
                </div>

            </div>
        </div>
        <div class="form-button" id="wizard-actions">
            <a id="btn_last"  class="btn btn-default btn-prev">取消</a>
            <a id="btn_finish"  class="btn btn-success">保存</a>
        </div>


    </div>

</form>
<%@include file="/common/js_common.jsp"%>

<script src="${root}/content/plugins/wizard/wizard.js"></script>
<script src="${root}/content/plugins/validator/validator.js"></script>

<script src="${root}/content/plugins/jqgrid/grid.locale-cn.js"></script>
<script src="${root}/content/plugins/jqgrid/jqgrid.js"></script>

<script src="${root}/content/plugins/wdtree/tree.js"></script>
<script src="${root}/js/menu/menu.js"></script>
<script type="text/javascript">
    var module = new ModuleMgr("${root}");
    module.loadForm();
    window.AcceptClick = module.AcceptClick;//用于回调触发
</script>
</body>
</html>
