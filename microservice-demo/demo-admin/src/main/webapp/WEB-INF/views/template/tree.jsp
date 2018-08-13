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

    <link href="${root}/content/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" />
</head>

<body>
<br/>
    <form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">

        <div class="control-group">
            <label class="control-label" style="width: 60px">树:</label>
            <div class="controls" style="margin-left: 40px">
                <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
            </div>
        </div>
    </form>
</body>

<!-- jQuery 2.2.3 -->
<script src="${root}/content/jquery-ztree/3.5.12/js/jquery-1.4.4.min.js"></script>

<script src="${root}/content/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>

<script>
    //声明对象
    var zTreeObj;

    // zTree 的参数配置
    var setting = {
        check: { //多选
            enable: true
        },
        data: { //多级
            simpleData: {
                enable: true
            }
        },
        callback: { //回调
            beforeClick: beforeClick,
            onClick: onClick,
            onCheck: onCheck
        }
    };

    // zTree 的数据属性
    var zNodes2 = [
        {
            name: "test1", open: true, children: [
            { name: "test1_1" }, { name: "test1_2" }]
        },
        {
            name: "test2", open: true, children: [
            { name: "test2_1" }, { name: "test2_2" }]
        }
    ]; //JSON多嵌套

    var zNodes = [
        { id: 1, pId: 0, name: "随意勾选 1", "dataType": "A", open: true },
        { id: 11, pId: 1, name: "随意勾选 1-1", "dataType": "B", open: false},
        { id: 111, pId: 11, name: "随意勾选 1-1-1" },
        { id: 112, pId: 11, name: "随意勾选 1-1-2" },
        { id: 12, pId: 1, name: "随意勾选 1-2", open: true },
        { id: 121, pId: 12, name: "随意勾选 1-2-1" },
        { id: 122, pId: 12, name: "随意勾选 1-2-2" },

        { id: 2, pId: 0, name: "随意勾选 2", open: true },
        { id: 21, pId: 2, name: "随意勾选 2-1" },
        { id: 22, pId: 2, name: "随意勾选 2-2", open: true },
        { id: 221, pId: 22, name: "随意勾选 2-2-1" },
        { id: 222, pId: 22, name: "随意勾选 2-2-2" },
        { id: 23, pId: 2, name: "随意勾选 2-13" }
    ]; //采用父级

    //加载
    $(document).ready(function () {
        $.ajax({
            type: "get",
            async:false,
            url: "${root}/treeList",                  //ajax请求地址
            success: function (data) {
                var json = eval('(' + data + ')');
                data = json.zTreeNodes;
                zTreeObj = $.fn.zTree.init($("#menuTree"), setting, data);

            },
        });

        //默认选中
        var node = zTreeObj.getNodeByParam("id", "0"); //根据id获取树的某个节点
        //zTreeObj.selectNode(node, false); //设置node节点选中状态
        zTreeObj.checkNode(node, true, true); //设置node节点checked选中


        $("#checkAllTrue").bind("click", { type: "checkAllTrue" }, checkNode); //全选
        $("#checkAllFalse").bind("click", { type: "checkAllFalse" }, checkNode); //全不选

        $("#btnSave").click(function () {
            //alert("当前选中项：");
            var zTreeObj = $.fn.zTree.getZTreeObj("menuTree");
            alert("当前选中项：" + getChildNodes(zTreeObj));
        });

    });

    var log, className = "dark";

    function beforeClick(treeId, treeNode, clickFlag) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeClick ]&nbsp;&nbsp;" + treeNode.name);
        return (treeNode.click != false);
    }
    function onClick(event, treeId, treeNode, clickFlag) {
        showLog("[ " + getTime() + " onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag === 1 ? "普通选中" : (clickFlag === 0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
    }
    function showLog(str) {
        if (!log) log = $("#log");
        log.append("<li class='" + className + "'>" + str + "</li>");
        if (log.children("li").length > 8) {
            log.get(0).removeChild(log.children("li")[0]);
        }
    }
    function getTime() {
        var now = new Date(),
            h = now.getHours(),
            m = now.getMinutes(),
            s = now.getSeconds();
        return (h + ":" + m + ":" + s);
    }

    //选中的数据
    function onCheck(e, treeId, treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("menuTree"),
            nodes = treeObj.getCheckedNodes(true),
            aaa = "", //自定义
            v = "";
        for (var i = 0; i < nodes.length; i++) {
            v += nodes[i].name + ",";

            if (typeof nodes[i].dataType != "undefined") {

                aaa += nodes[i].dataType + ",";

            }
            //alert(nodes[i].id); //获取选中节点的值
        }

        if (v.length > 0)
            v = v.substring(0, v.length - 1);
        var cityObj = $("#citySel");
        cityObj.attr("value", v);

        alert(v);
    }


    //功能：获取当前选中节点的子节点id集合。
    //步骤：1.获取当前节点
    //2.用ztree的方法transformToArray()获取当前选中节点（含选中节点）的子节点对象集合。
    //3.遍历集合，取出需要的值。
    //treeNode：当前选中节点对象
    function getChildNodes(treeNode) {
        //alert(treeNode);
        var childNodes = zTreeObj.getCheckedNodes(true);
        var nodes = new Array();
        for (i = 0; i < childNodes.length; i++) {
            nodes[i] = childNodes[i].id;
        }
        return nodes.join(",");
    }

    function checkNode(e) {
        var zTree = $.fn.zTree.getZTreeObj("menuTree"),
            type = e.data.type,
            nodes = zTree.getSelectedNodes();
        if (type.indexOf("All") < 0 && nodes.length == 0) {
            alert("请先选择一个节点");
        }

        if (type == "checkAllTrue") {
            zTree.checkAllNodes(true);
        } else if (type == "checkAllFalse") {
            zTree.checkAllNodes(false);
        } else {
            var callbackFlag = $("#callbackTrigger").attr("checked");
            for (var i = 0, l = nodes.length; i < l; i++) {
                if (type == "checkTrue") {
                    zTree.checkNode(nodes[i], true, false, callbackFlag);
                } else if (type == "checkFalse") {
                    zTree.checkNode(nodes[i], false, false, callbackFlag);
                } else if (type == "toggle") {
                    zTree.checkNode(nodes[i], null, false, callbackFlag);
                } else if (type == "checkTruePS") {
                    zTree.checkNode(nodes[i], true, true, callbackFlag);
                } else if (type == "checkFalsePS") {
                    zTree.checkNode(nodes[i], false, true, callbackFlag);
                } else if (type == "togglePS") {
                    zTree.checkNode(nodes[i], null, true, callbackFlag);
                }
            }
        }
    }

</script>
</html>