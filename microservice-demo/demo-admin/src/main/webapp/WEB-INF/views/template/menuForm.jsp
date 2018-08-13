<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

<head>
    <meta charset="utf-8" />
    <title>SuperUI前端框架</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${root}/content/ui/global/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link href="${root}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <!-- Theme style -->
    <link rel="stylesheet" href="${root}/content/adminlte/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${root}/content/adminlte/dist/css/skins/_all-skins.min.css">
    <link href="${root}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
    <link href="${root}/content/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
	<style type="text/css">  
            #content {  
                width: 400px;  
                height: 200px;  
            }  
              
            #tab_bar {  
                width: 400px;  
                height: 25px;  
                float: left;  
            }  
            #tab_bar ul {  
                padding: 0px;  
                margin: 0px;  
                height: 20px;  
                text-align: center;  
            }
              
            #tab_bar li {  
                list-style-type: none;  
                float: left;  
                width: 133.3px;  
                height: 25px;  
                background-color: gray;  
            }  
              
            .tab_css {  
                width: 400px;  
                height: 200px;  
                background-color: orange;  
                display: none;  
                float: left;  
            }  
        </style> 
</head>
<body>
<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-2">
           	<strong>菜单管理</strong>
            <div class="box box-success bordered">
                <div class="box-body">
                    <div id="tree_1" class="tree-demo">
                        <!-- <ul>
                            <li>
                                根节点
                                <ul>
                                    <li data-jstree='{ "selected" : true }'>
                                        <a href="javascript:;"> 初始选择 </a>
                                    </li>
                                    <li data-jstree='{ "icon" : "fa fa-briefcase icon-state-success " }'> 用Uzi定义图标 </li>
                                    <li data-jstree='{ "opened" : true }'>
                                        初始展开效果
                                        <ul>
                                            <li data-jstree='{ "disabled" : true }'> 禁用某个节点 </li>
                                            <li data-jstree='{ "type" : "file" }'> 其他节点 </li>
                                        </ul>
                                    </li>
                                    <li data-jstree='{ "icon" : "fa fa-warning icon-state-danger" }'> 用户自定义图标（bootstrap风格）</li>
                                </ul>
                            </li>
                            <li data-jstree='{ "type" : "file" }'>
                                <a href="http://www.jstree.com">可点击节点 </a>
                            </li>
                        </ul> -->
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-10" style="display:none" id="d1">
        	<div id="tab_bar">  
                <ul>  
                    <li id="tab1" onclick="myclick(1)" style="background-color: blue">菜单信息</li>  
                   <!--  <li id="tab2" onclick="myclick(2)" >菜单列表</li>   -->
                </ul>  
            </div>  
        	<div class="box box-success bordered">	
        	</div>
       		<div id="content">
	            <div class="tab_css" id="tab1_content" style="display: block">  
	                <div>页面一</div>  
	                <div id="form1" method="post">
        <table style="width:100%;height:100%;table-layout:fixed;" class="nui-form-table" >
           <tr class="odd">
                <th class="nui-form-label"><label for="appmenu.menuId$text">菜单id：</label></th>
                <td>    
                    <input id="appmenu.menuId" name="appmenu.menuId" class="nui-textbox nui-form-input nui-form-input" enabled="false"/>
                </td>
                <th class="nui-form-label"><label for="appmenu.menuCode$text">菜单代码：</label></th>
                <td>    
                    <input id="appmenu.menuCode" name="appmenu.menuCode" class="nui-textbox nui-form-input"
                     required="true" />
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
            </tr>
            <tr>
            	<th class="nui-form-label"><label for="test1$text">功能描述：</label></th>
	            <td>
	                <input id="appmenu.functionDesc" name="appmenu.functionDesc"  class="nui-textbox nui-form-input" />
	            </td>
            </tr>
        </table>
        <div class="nui-toolbar" style="text-align:center;padding-top:5px;padding-bottom:5px;" borderStyle="border:0;">
	        <a class="nui-button" style="width:60px;" iconCls="icon-save" onclick="SaveData()">保存</a>
	    </div>        
    </div>
	            </div>  
	            <div class="tab_css" id="tab2_content" style="display:none">  
	                <div>页面二</div>  
	            </div>  
	        </div>  
        </div>
    </div>
</section>

<!-- jQuery 2.2.3 -->
<script src="${root}/content/ui/global/jQuery/jquery.min.js"></script>
<script src="${root}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
<script src="${root}/content/min/js/supershopui.common.js"></script>
<script src="${root}/content/plugins/jstree/dist/jstree.min.js"></script>
<script type="text/javascript">

    //nui.parse();
    getMenuData();
	function getMenuData(){
		$.ajax({
			url: "${root}/getMenuData",
			type: "POST",
			success: function(text){
				//alert(text);
				var treeNodes = text.treeNodes;
				setMenuData(treeNodes);
			}
		});
	}
	
	function setMenuData(data){
		var sb = ' [{ "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },'+
		'{ "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },'+
		'{ "id" : "ajson3", "parent" : "ajson2", "text" : "Child 1" },'+
		'{ "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" }]';
		var men = '[{ "id" : "0", "parent" : "#", "text" : "菜单管理" },';
		if(data){
			for(var i = 0; i < data.length; i++){
				var menuName = data[i].menuName;
				var linkAction = data[i].functionPath ? data[i].functionPath: "";
				var menuPrimeKey = data[i].menuId;
				var menuSeq = data[i].menuSeq;
				men = men + "{";
				men = men + "\"id\":"+"\""+menuPrimeKey+"\""+",";
				men = men + "\"text\":"+"\""+menuName+"\""+",";
				men = men + "\"parent\": \"0\"";
				men = men +"},";
				var secondChilds = "";
				if(data[i].childrenMenuTreeNodeList){
					var Lev2childrens = data[i].childrenMenuTreeNodeList;
					for(var j = 0; j < Lev2childrens.length; j++){
						var menuName = Lev2childrens[j].menuName;
						var linkAction = Lev2childrens[j].functionPath ? Lev2childrens[j].functionPath : "";
						var menuPrimeKey = Lev2childrens[j].menuId;
						var menuSeq = Lev2childrens[j].menuSeq;
						men = men + "{";
						men = men + "\"id\":"+"\""+menuPrimeKey+"\""+",";
						men = men + "\"text\":"+"\""+menuName+"\""+",";
						men = men + "\"parent\": \""+data[i].menuId+"\"";
						men = men + "},";
					}
				}
			}
		}
		men = men.substring(0,men.length-1);
		men = men + "]";
		var strb=JSON.parse(men);
		var treeId = "tree_1";
		var url = "${root}/menu/addMenuForm";
		$("#"+treeId).jstree({
                "plugins" : ["contextmenu", "dnd", "search",  
                             "state", "types", "wholerow"],
                 'core' : {
                 'data' :  
					strb
                 },
                 "contextmenu":{  
                     "items":{  
                         "create":null,  
                         "rename":null,  
                         "remove":null,  
                         "ccp":null,  
                         "新建菜单":{  
                             "label":"新建菜单",  
                             "action":function(data){  
                                 var inst = jQuery.jstree.reference(data.reference),  
                                 obj = inst.get_node(data.reference);
                                  layer.open({
                                	 type:2,
                                	 title:"新建“"+obj.text+"”的子菜单",
                                	 content:url+"?id="+obj.id,
                                	 area:["50%","50%"]});  
                             }  
                         },  
                         "删除菜单":{  
                             "label":"删除菜单",  
                             "action":function(data){  
                                 var inst = jQuery.jstree.reference(data.reference),  
                                 obj = inst.get_node(data.reference);  
                                 if(confirm("确定要删除此菜单？删除后不可恢复。")){  
                                     jQuery.get("/accountmanage/deleteMenu?id="+obj.id,function(dat){  
                                         if(dat == 1){  
                                             alert("删除菜单成功！");  
                                             jQuery("#"+treeid).jstree("refresh");  
                                         }else{  
                                             alert("删除菜单失败！");  
                                         }  
                                     });  
                                 }  
                             }  
                         }}}
		}).bind('click.jstree', function(event) {  
			document.getElementById("d1").style.display="block";
			var id = event.target.id;
			var num = id.indexOf("_anchor");
			id = id.substr(0,num);
			$.ajax({
				url: "${root}/menu/getMenuDataInfo?id="+id,
				type: "POST",
				success: function(text){
					if(text.isSuccess == true){
						var menuInfo = text.menuInfo;
					}
				}
			});
		   });
		jQuery("#"+treeId).parents("td").height((jQuery("#"+treeId).height()+"50")+"px");  
	}
	
</script>
 <script type="text/javascript">  
            var myclick = function(v) {  
//                 var llis = document.getElementsByTagName("li");  
                var llis = $("#tab_bar ul li");
                for(var i = 0; i < llis.length; i++) {  
                    var lli = llis[i];  
                    if(lli == document.getElementById("tab" + v)) {  
                        lli.style.backgroundColor = "blue";  
                    } else {  
                        lli.style.backgroundColor = "gray";  
                    }  
                }  
  
                var divs = document.getElementsByClassName("tab_css");  
                for(var i = 0; i < divs.length; i++) {  
  
                    var divv = divs[i];  
  
                    if(divv == document.getElementById("tab" + v + "_content")) {  
                        divv.style.display = "block";  
                    } else {  
                        divv.style.display = "none";  
                    }  
                }  
  
            }  
        </script> 
</body>
</html>