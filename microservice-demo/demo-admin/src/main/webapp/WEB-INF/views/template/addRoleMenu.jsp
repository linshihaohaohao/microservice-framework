<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false" %>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${request.contextPath}"/>
<%
	String roleId=request.getParameter("roleId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
	<title>配置角色关联菜单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script src="${root}/js/jquery-1.9.1.min.js"></script>
	<link href="${root}/content/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" />
	<script src="${root}/content/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${root}/js/nui-min.js"></script>
</head>
<body style="overflow:hidden;width:100%;">
	<div class="nui-fit">
		<div class="box-body">
             <label class="control-label" style="width: 60px">菜单列表:</label>
            <div class="controls" style="margin-left: 40px">
                <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
            </div>
        </div>
	</div>
	<div class="nui-toolbar" style="padding:0px;" borderStyle="border:0;">
	    <table width="100%">
	      <tr>
	        <td style="text-align:center;">
	          <button class="nui-button" style="width:60px;" iconCls="icon-save" id="btnSave" name="btnSave">保存</button>
	          <a class="nui-button" iconCls="icon-cancel" onclick="reback()">关闭</a>
	        </td>
	      </tr>
	    </table>
	    <div id="form1" method="post">
	     <table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table" >
	     	 <tr class="odd">
	     	 <td>    
	     	 		<input id="roleId" value="<%=roleId%>"  class="nui-hidden">
                    <input id="oldRoleMenu" name="oldRoleMenu"  class="nui-hidden" />
                    <input id="newRoleMenu" name="newRoleMenu"  class="nui-hidden"/>
                </td>
	     	 </tr>
	     </table>
	    </div>
	 </div>

<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name);
function reback(){
	parent.$('#returnValue').val("");
	parent.layer.close(index);
}

var userName = parent.parent.$('#userName').html();
userName = userName.substr(userName.indexOf("用户名：")+4,userName.length);
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

var zNodes = [
              { id: 1, pId: 0, name: "随意勾选 1", "dataType": "A", open: true },
              { id: 11, pId: 1, name: "随意勾选 1-1", "dataType": "B", open: false},
              { id: 111, pId: 11, name: "随意勾选 1-1-1" },
              { id: 112, pId: 11, name: "随意勾选 1-1-2" },
              { id: 12, pId: 1, name: "随意勾选 1-2", open: true },
              { id: 121, pId: 12, name: "随意勾选 1-2-1" },
              { id: 122, pId: 12, name: "随意勾选 1-2-2" },

              { id: 2, pId: 0, name: "随意勾选 2", open: false },
              { id: 21, pId: 2, name: "随意勾选 2-1" },
              { id: 22, pId: 2, name: "随意勾选 2-2", open: false },
              { id: 221, pId: 22, name: "随意勾选 2-2-1" },
              { id: 222, pId: 22, name: "随意勾选 2-2-2" },
              { id: 23, pId: 2, name: "随意勾选 2-13" }
          ]; //采用父级

          //加载
          $(document).ready(function () {
        	  $.getJSON("${root}/getRoleMenuTreeNode?roleId=<%=roleId%>",function(data){
        		var oldMenu = ""
        		if(data != null){
        			for(var i=0;i<data.length;i++){
        				if(data[i].checked){
        					oldMenu += data[i].id+":"+data[i].menuCode + ",";
        				}
        			}
        			document.getElementById("oldRoleMenu").value=oldMenu;
        		}
  				$.fn.zTree.init($("#menuTree"), setting, data);
  			});
             <%--  $.ajax({
                  type: "get",
                  async:false,
                  type:"json",
                  url: "${root}/getRoleMenuTreeNode?roleId=<%=roleId%>",                  //ajax请求地址
                  success: function (data) {
                      var json = eval('(' + data + ')');
                      data = json.zTreeNodes;
                      zTreeObj = $.fn.zTree.init($("#menuTree"), setting, data);

                  },
              }); --%>

              //默认选中
             // var node = zTreeObj.getNodeByParam("id", "0"); //根据id获取树的某个节点
              //zTreeObj.selectNode(node, false); //设置node节点选中状态
              //zTreeObj.checkNode(node, true, true); //设置node节点checked选中


            //  $("#checkAllTrue").bind("click", { type: "checkAllTrue" }, checkNode); //全选
           //   $("#checkAllFalse").bind("click", { type: "checkAllFalse" }, checkNode); //全不选

              $("#btnSave").click(function () {
            	  var newRoleMenu = "";
                  var treeObj = $.fn.zTree.getZTreeObj("menuTree"),
                      nodes = treeObj.getCheckedNodes(true),
                      aaa = "", //自定义
                      v = "";
                  for (var i = 0; i < nodes.length; i++) {
                      v += nodes[i].name + ",";

                      if (typeof nodes[i].dataType != "undefined") {

                          aaa += nodes[i].dataType + ",";

                      }
                     newRoleMenu += nodes[i].id+":"+nodes[i].menuCode+","; //获取选中节点的值
                      
                  }
          		var roleDesc = $("#oldRoleMenu").val();
          		var roleId = $("#roleId").val();
          		var json = nui.encode({newRoleMenu:newRoleMenu,oldRoleMenu:oldRoleMenu,roleId:roleId,userName:userName});
                  $.ajax({
                     url: "${root}/saveRoleMenu",
                     type: 'POST',
                     data: json,
                     cache: false,
                     contentType:'application/json; charset=UTF-8',
                     success: function (text) {
                    	 var returnJson = nui.decode(text);
         				var result = returnJson.isSuccess;
         				var responseMsg = returnJson.responseMsg;
         				if(result == true){
         	            		parent.$('#returnValue').val("success");
         		            	parent.layer.close(index);
         	            }else if(result == "error" ){
         	            	nui.alert(responseMsg,"温馨提示");
         	            }
                     },
                     error: function (jqXHR, textStatus, errorThrown) {
                     	nui.alert("保存失败");
                     }
                 });
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
        	 /*  var newRoleMenu = "";
              var treeObj = $.fn.zTree.getZTreeObj("menuTree"),
                  nodes = treeObj.getCheckedNodes(true),
                  aaa = "", //自定义
                  v = "";
              for (var i = 0; i < nodes.length; i++) {
                  v += nodes[i].name + ",";

                  if (typeof nodes[i].dataType != "undefined") {

                      aaa += nodes[i].dataType + ",";

                  }
                 newRoleMenu += nodes[i].id+":"+nodes[i].menuCode+","; //获取选中节点的值
                  
              }
              //document.getElementById("newRoleMenu").value = newRoleMenu;
              if (v.length > 0)
                  v = v.substring(0, v.length - 1);
              var cityObj = $("#citySel");
              cityObj.attr("value", v);

              alert(v); */
          }

          <%-- 关闭窗口 --%>
      	function CloseWindow(action){
      		if(action=="close" && form1.isChanged()){
      			if(confirm("数据已改变,是否先保存?")){
      				return false;
      			}
      		}else if(window.CloseOwnerWindow){
      			return window.CloseOwnerWindow(action);
      		}else{
      			return window.close();
      		}
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
</body>
</html>