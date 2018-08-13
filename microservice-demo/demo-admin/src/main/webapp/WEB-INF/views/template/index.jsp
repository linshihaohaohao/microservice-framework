<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title>首页</title>

    <%@include file="/common/meta.jsp"%>
    <style type="text/css">
        html {
            overflow: hidden;
        }
    </style>
	<%
	UserInfo userInfo = new UserInfo();
	userInfo = (UserInfo) session.getAttribute("user");
	String userCode = userInfo.getAccount();
	String userName = userInfo.getRealName();
	%>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body class="hold-transition skin-blue sidebar-mini fixed">
<div class="wrapper">
    <!-- Main Header -->
    <header class="main-header">
        <!-- Logo -->
        <a href="" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
           <!--  <span class="logo-mini"><b>S</b>UI</span> -->
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>B2B商城管理后台</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">切换导航</span>
            </a>
           <%--  <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="${root}/views/template/add.jsp">系统设置<span class="sr-only">(current)</span></a></li>
                    <li><a href="#">订单</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">会员 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">会员积分管理</a></li>
                            <li><a href="#">会员等级管理</a></li>
                            <li><a href="#">会员信息管理</a></li>
                            <li class="divider"></li>
                            <li><a href="#">会员营销管理</a></li>
                            <li class="divider"></li>
                            <li><a href="#">会员优惠券</a></li>
                        </ul>
                    </li>
                </ul>

            </div> --%>
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!-- The user image in the navbar-->
                            <%-- <img src="${root}/content/ui/img/photos/girl1.png" class="user-image" alt="User Image"> --%>
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs" name="userName" id="userName">TZHSWEET</span>
                        </a>
                         <%-- <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                           <li class="user-header">
                                <img src="${root}/content/ui/img/photos/girl1.png" class="img-circle" alt="User Image">
                                <p>
                                    TZHSWEET的小妞
                                    <small>2016年注册</small>
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-4 text-center">
                                        <a href="#">个人信息</a>
                                    </div>
                                    <div class="col-xs-4 text-center">
                                        <a href="#">设置</a>
                                    </div>
                                    <div class="col-xs-4 text-center">
                                        <a href="#">主题</a>
                                    </div>
                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <!-- <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">个人中心</a>
                                </div> -->
                                <div class="pull-right">
                                    <a href="#" class="btn btn-default btn-flat">退出</a>
                                </div>
                            </li>
                        </ul> --%>
                    </li>
                    <li>
                    	<a href="#" class="btn dropdown-toggle" onclick="logout()">退出登录</a>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                    <li>
                        <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- Sidebar user panel (optional) -->
            <%-- <div class="user-panel">
                <div class="pull-left image">
                    <img src="${root}/content/ui/img/photos/girl1.png" class="img-circle" alt="用户头像">
                </div>
                <div class="pull-left info">
                    <p name="userName">TZHSWEET</p>
                    <!-- Status -->
                   <!--  <a href="#"><i class="fa fa-circle text-success"></i> 在线</a> -->
                </div>
            </div> --%>
            <!-- search form (Optional) -->
           <!--  <form action="#" method="get" class="sidebar-form">
                <div class="input-group">
                    <input type="text" name="q" class="form-control" placeholder="Search...">
                    <span class="input-group-btn">
                            <button type="submit" name="search" id="search-btn" class="btn btn-flat">
                                <i class="fa fa-search"></i>
                            </button>
                        </span>
                </div>
            </form> -->
            <!-- /.search form -->
            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">

            </ul>
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper"  id="content-wrapper">
        <div class="content-tabs">
            <button class="roll-nav roll-left tabLeft" onclick="scrollTabLeft()">
                <i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs menuTabs tab-ui-menu" id="tab-menu">
                <div class="page-tabs-content" style="margin-left: 0px;">

                </div>
            </nav>
            <button class="roll-nav roll-right tabRight" onclick="scrollTabRight()">
                <i class="fa fa-forward" style="margin-left: 3px;"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown tabClose" data-toggle="dropdown">
                    页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-right" style="min-width: 128px;">
                    <li><a class="tabReload" href="javascript:refreshTab();">刷新当前</a></li>
                    <li><a class="tabCloseCurrent" href="javascript:closeCurrentTab();">关闭当前</a></li>
                    <li><a class="tabCloseAll" href="javascript:closeOtherTabs(true);">全部关闭</a></li>
                    <li><a class="tabCloseOther" href="javascript:closeOtherTabs();">除此之外全部关闭</a></li>
                </ul>
            </div>
            <button class="roll-nav roll-right fullscreen" ><i class="fa fa-arrows-alt"></i></button>
        </div>
        <div class="content-iframe " style="background-color: #ffffff; ">
            <div class="tab-content " id="tab-content">

            </div>
        </div>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <%@include file="/common/foot.jsp"%>
    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
            <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane" id="control-sidebar-home-tab">
                <h3 class="control-sidebar-heading">最近活动</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-birthday-cake bg-red"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>
                                <p>Will be 23 on April 24th</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-user bg-yellow"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>
                                <p>New phone +1(800)555-1234</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>
                                <p>nora@example.com</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-file-code-o bg-green"></i>
                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>
                                <p>Execution time 5 seconds</p>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->
                <h3 class="control-sidebar-heading">Tasks Progress</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Custom Template Design
                                <span class="label label-danger pull-right">70%</span>
                            </h4>
                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Update Resume
                                <span class="label label-success pull-right">95%</span>
                            </h4>
                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-success" style="width: 95%"></div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Laravel Integration
                                <span class="label label-warning pull-right">50%</span>
                            </h4>
                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Back End Framework
                                <span class="label label-primary pull-right">68%</span>
                            </h4>
                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->
            </div>
            <!-- /.tab-pane -->
            <!-- Stats tab content -->
            <div class="tab-pane" id="control-sidebar-stats-tab">状态面板设置</div>
            <!-- /.tab-pane -->
            <!-- Settings tab content -->
            <div class="tab-pane" id="control-sidebar-settings-tab">
                <form method="post">
                    <h3 class="control-sidebar-heading">常规设置</h3>
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Report panel usage
                            <input type="checkbox" class="pull-right" checked>
                        </label>
                        <p>
                            Some information about this general settings option
                        </p>
                    </div>
                    <!-- /.form-group -->
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Allow mail redirect
                            <input type="checkbox" class="pull-right" checked>
                        </label>
                        <p>
                            Other sets of options are available
                        </p>
                    </div>
                    <!-- /.form-group -->
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Expose author name in posts
                            <input type="checkbox" class="pull-right" checked>
                        </label>
                        <p>
                            Allow the user to show his name in blog posts
                        </p>
                    </div>
                    <!-- /.form-group -->
                    <h3 class="control-sidebar-heading">Chat Settings</h3>
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Show me as online
                            <input type="checkbox" class="pull-right" checked>
                        </label>
                    </div>
                    <!-- /.form-group -->
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Turn off notifications
                            <input type="checkbox" class="pull-right">
                        </label>
                    </div>
                    <!-- /.form-group -->
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Delete chat history
                            <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
                        </label>
                    </div>
                    <!-- /.form-group -->
                </form>
            </div>
            <!-- /.tab-pane -->
        </div>
    </aside>
    <!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed
    immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<!-- REQUIRED JS SCRIPTS -->
<!-- jQuery 2.2.3 -->
<%@include file="/common/js_common.jsp"%>


<%-- <%@include file="/common/menu.jsp"%> --%>
</body>
<script type="text/javascript">
	var userName = '<%=userName %>';
	load();
	function load(){
		var x=document.getElementsByName("userName");
		for(var i=0;i<x.length;i++){
			x[i].innerHTML = "用户名："+userName;
		}
	}
	getMenuData();
	function getMenuData(){
		$.ajax({
			url: "${root}/getMenuData",
			type: "POST",
			success: function(text){
				var treeNodes = text.treeNodes;
				setMenuData(treeNodes);
			}
		});
	}
	
	function setMenuData(data){
		if(data){
			var men = "[";
			var ss = "${root}";
			ss = ss.substring(1,ss.length);
			for(var i = 0; i < data.length; i++){
				var menuName = data[i].menuName;
				var linkAction = data[i].functionPath ? data[i].functionPath: "";
				var menuPrimeKey = data[i].menuId;
				var menuSeq = data[i].menuSeq;
				men = men + "{";
				men = men + "id:"+"\""+menuPrimeKey+"\""+",";
				men = men + "text:"+"\""+menuName+"\""+",";
				men = men + "icon: \"fa fa-circle-o\",";
				var secondChilds = "";
				if(data[i].childrenMenuTreeNodeList){
					var Lev2childrens = data[i].childrenMenuTreeNodeList;
					men = men + "children: [";
					for(var j = 0; j < Lev2childrens.length; j++){
						var menuName = Lev2childrens[j].menuName;
						var linkAction = Lev2childrens[j].functionPath ? Lev2childrens[j].functionPath : "";
						var menuPrimeKey = Lev2childrens[j].menuId;
						var menuSeq = Lev2childrens[j].menuSeq;
						men = men + "{";
						men = men + "id:"+"\""+menuPrimeKey+"\""+",";
						men = men + "text:"+"\""+menuName+"\""+",";
						men = men + "targetType: \"iframe-tab\",";
						men = men + "url: "+"\""+ss+linkAction+"\",";
						men = men + "icon: \"fa fa-table\",";
						men = men + "},";
					}
					men = men + "]";
				}
				men = men +"},";
			}
			men = men + "]";
			var strb=eval(men);
			//addTabs(({ id: '10008', title: '欢迎页', close: false, url: '${root}/dishBoard' }));
			App.fixIframeCotent();
			var menus = [
			             /* { id: "10010", text: "我的工作台", isHeader: true }, */
			             {
			                 id: "10208",
			                 text: "表格组件",

			                 icon: "fa fa-circle-o",
			                 children: [
			                     { id: "10212", text: "管理表格", targetType: "iframe-tab", url: "${root}/list", icon: "fa fa-table" },
			                     { id: "10212", text: "管理表格", targetType: "iframe-tab", url: "${root}/list", icon: "fa fa-table" },
			                 ]
			             },
			             {
			                 id: "10208",
			                 text: "表格组件1",

			                 icon: "fa fa-circle-o",
			                 children: [
			                     { id: "10213", text: "管理表格1", targetType: "iframe-tab", url: "${root}/list1", icon: "fa fa-table" },
			                 ]
			             }
			         ];
			 $('.sidebar-menu').sidebarMenu({ data: strb, param: { strUser: 'admin' } });
		}
		
	}
	
	//退出登录
	function logout(){
		$.ajax({
			url: "${root}/logout",
			type: "POST",
			success: function(data){
				 var isSuccess = data.isSuccess;
		         if( isSuccess == true ){
	         		window.location.href="${root}/toLogin";
		         }else{
		         	alert(data.responseMsg);
		         } 
			}
		});
	}
</script>
</html>
