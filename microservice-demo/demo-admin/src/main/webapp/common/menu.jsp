<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
    $(function() {
        addTabs(({ id: '10008', title: '欢迎页', close: false, url: '${root}/dishBoard' }));
        App.fixIframeCotent();
        var menus = [
            /* { id: "10010", text: "我的工作台", isHeader: true }, */
            {
                id: "10208",
                text: "表格组件",

                icon: "fa fa-circle-o",
                children: [
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
            },
            /*{
                id: "30209", text: "页面实例", isOpen: false, icon: "fa fa-circle-o", children: [
                {
                    id: "40208",
                    text: "SuperMgr后台Demo",
                    icon: "fa fa-circle-o",
                    targetType: "blank", url: "../pages/supermgr/index.html"
                }
            ]

            }*/
        ];
        $('.sidebar-menu').sidebarMenu({ data: menus, param: { strUser: 'admin' } });


    });
</script>