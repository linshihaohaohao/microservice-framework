<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<%@page import="java.util.HashSet,cn.com.bluemoon.admin.domain.vo.MallSysMenuVo" %>
<%
	String skin="skin1";
	String contextPath=request.getContextPath();
	
    UserInfo userObject = (UserInfo)request.getSession().getAttribute("user");
	String empcode = userObject!=null?userObject.getAccount():"";
    String empname = userObject!=null?userObject.getRealName():"";
    HashSet roleSet = (HashSet)request.getSession().getAttribute("roles");
    MallSysMenuVo menuVo = (MallSysMenuVo)request.getAttribute("menuVo");
%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/common/meta.jsp"%>
	<script type="text/javascript" src="${root}/js/nui.js"></script>
	<script src="${root}/js/jquery-1.9.1.min.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div style="padding-top:5px">
<div id="form1" method="post">
 <table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table" >
 <tr class="odd">
                <th class="nui-form-label"><label for="appmenu.menuId$text">菜单id：</label></th>
                <td>    
                    <input id="appmenu.menuId" name="appmenu.menuId" class="form-control" disabled="true"/>
                </td>
                <th class="nui-form-label"><label for="appmenu.menuCode$text">菜单代码：</label></th>
                <td>    
                    <input id="appmenu.menuCode" name="appmenu.menuCode" class="form-control"
                     required="true"  onvalidation="codevalidation"/>
                </td>
</tr>
<tr>
                <th class="nui-form-label"><label for="appmenu.menuName$text">菜单名称：</label></th>
                <td>    
                    <input id="appmenu.menuName" name="appmenu.menuName" class="nui-textbox nui-form-input" required="true" />
                    <input id="appmenu.menuLevel" name="appmenu.menuLevel" class="nui-hidden"/>
                    <input id="appmenu.menuSeq" name="appmenu.menuSeq" class="nui-hidden"/>
                    <input id="appmenu.parentsId" name="appmenu.parentsId" class="nui-hidden"/>
                    <input id="appmenu.menuType" name="appmenu.menuType" class="nui-hidden"/>
                    <input id="appmenu.parentsCode" name="appmenu.parentsCode" class="nui-hidden"/>
                </td>
                <th class="nui-form-label"><label for="appmenu.menuLabel$text">菜单显示名称：</label></th>
                <td>    
                    <input id="appmenu.menuLabel" name="appmenu.menuLabel" class="nui-textbox nui-form-input" required="true"  vtype="rangeLength:1,20"/>
                </td>
            </tr>
            <tr>
                <th class="nui-form-label"><label for="appmenu.displayOrder$text">菜单显示顺序：</label></th>
                <td>    
                    <input id="appmenu.displayOrder" name="appmenu.displayOrder" class="nui-textbox nui-form-input" required="true"  vtype="range:0,100"/>
                </td>
                <th class="nui-form-label"><label for="test1$text">功能描述：</label></th>
	            <td>
	                <input id="appmenu.functionDesc" name="appmenu.functionDesc"  class="nui-textbox nui-form-input" />
	            </td>
            </tr>
            <tr class="pc">
            	<th class="nui-form-label"><label for="test1$text">页面路径：</label></th>
                <td colspan="3">
                	<input id="appmenu.functionPath" name="appmenu.functionPath" onvalidation="pagePathValid" 
                	class="nui-textbox nui-form-input" />
                </td>
            </tr>
 </table>
 <div class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;" borderStyle="border:0;">
	        <button class="nui-button" style="width:60px;" iconCls="icon-save" onclick="onOk()">保存</button>
	        <button class="nui-button" style="width:60px;" iconCls="icon-cancel" onclick="onCancel()">取消</button>
	    </div> 
</div>
</div>
<%@include file="/common/js_common.jsp"%>
<script src="${root}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript">
nui.parse();
    function reback(){
        location.href = ("${root}/list");
    }
    var menuName = "<%=menuVo.getMenuName()%>";
    var menuLevel = "<%=menuVo.getMenuLevel()%>";
    var id = "<%=menuVo.getMenuId()%>";
    var menuType = "<%=menuVo.getMenuType()%>";
    var menuSeq = "<%=menuVo.getMenuSeq()%>";
    var menuCode = "<%=menuVo.getMenuCode()%>";
    var menuVo = new Object();
    menuVo.menuLevel = menuLevel;
    menuVo.menuSeq = menuSeq;
    menuVo.id = id;
    menuVo.menuType = menuType;
    menuVo.menuCode = menuCode;
    setData(menuVo);
    function setData(data) {
        //跨页面传递的数据对象，克隆后才可以安全使用
        var node=data;
        nui.get("appmenu.menuLevel").setValue(parseInt(node.menuLevel)+1);
        nui.get("appmenu.menuSeq").setValue(node.menuSeq);
        nui.get("appmenu.menuType").setValue(node.menuType);
        nui.get("appmenu.parentsId").setValue(node.id);
        nui.get("appmenu.parentsCode").setValue(node.menuCode);
    }
  
</script>
<script type="text/javascript">
nui.parse();
var form = new nui.Form("form1");

function onOk(e) {
    SaveData();
}
function onCancel(e) {
    CloseWindow("cancel");
}
function SaveData() {
    var o = form.getData(true,true);
    var menu=o.appmenu;
    form.validate();
    if (form.isValid() == false) return;
    var menuType=menu.menuType;
    if("pc"==menuType){
    	var isleaf=menu.ifleaf;
    	var functionPath=menu.functionPath;
        if(isleaf==1&&functionPath==""){
       		nui.alert("叶子菜单,请输入页面路径");
       		return;
        }
    }
    var json = nui.encode(o);
     $.ajax({
        url: "<%=request.getContextPath()%>/menu/addMenu",
        type: 'POST',
        data: json,
        cache: false,
        contentType:'application/json; charset=UTF-8',
        success: function (text) {
            nui.alert("保存成功");
            CloseWindow("ok");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	nui.alert("保存失败");
        }
    }); 
}

function CloseWindow(action) {            
    if (action == "close" && form.isChanged()) {
        if (confirm("数据被修改了，是否先保存？")) {
            return false;
        }
    }
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();            
}
//路径必须以/开头
function pagePathValid(e) {
	var functionPath=nui.get("appmenu.functionPath").getValue();
	/* if(functionPath==""||functionPath=="#"){//如果没有填写路径 就不用验证，否则就得必填 而且/开头
		return true;
	} */
	var re=/\/+/;
    if (re.test(e.value)){
    	return true;
    }else{
    	 e.errorText = "路径必须以/开头";
         e.isValid = false;
         return false;
    }
}
</script>
</body>
</html>
