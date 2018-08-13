var ModuleMgr = function (basePath) {
    var exports = {};
    exports.options = {};
    exports.options.ParentId = "";
    if(basePath != null && "" != basePath){
    	exports.path = basePath;
    }else{
    	exports.path = "";
    }

    //初始化数据
    exports.initGridPage = function () {
        //layout布局
        $('#layout').layout({
            applyDemoStyles: false,
            onresize: function () {
                $(window).resize();
            },
            height: $.fn.getLayoutHeight()
        });
        //初始化
        $(window).resize(function (e) {
            window.setTimeout(function () {
                $("#layout").css({ "height": $.fn.getLayoutHeight() });
                $('#gridTable').setGridWidth(($('.gridPanel').width()));
                $("#gridTable").setGridHeight($.fn.getGridHeight());
                $("#itemTree").setTreeHeight($.fn.getLayoutContentHeight());
            }, 100);
            e.stopPropagation();
        });

        //查询条件
        $("#queryCondition .dropdown-menu li").click(function () {
            var text = $(this).find('a').html();
            var value = $(this).find('a').attr('data-value');
            $("#queryCondition .dropdown-text").html(text).attr('data-value', value);
        });

        //查询回车
        $('#txt_Keyword').bind('keypress', function (event) {
            if (event.keyCode == "13") {
                $('.lr-search').trigger("click");
            }
        });
        //注册事件
        $(".titlePanel").on("click", ".lr-replace,.lr-add,.lr-edit,.lr-delete,.lr-search", function () {
            var $this = $(this);
            //添加地址
            if ($this.hasClass('lr-replace')) {
                reload();
            }
            else if ($this.hasClass('lr-add')) {
                exports.btnAdd();
            } else if ($this.hasClass('lr-edit')) {
                exports.btnEdit();
            } else if ($this.hasClass('lr-delete')) {
                exports.btnDelete();
            }  else if ($this.hasClass('lr-search')) {
                exports.btnSearch();
            }
        });

    };

    //加载树
    exports.loadTree = function () {
        var item = {
            height: $.fn.getLayoutContentHeight(),
            url:  exports.path +  "/queryMenuData",
            // url: "/content/supermgr/json/ModuleTree.json",
            onnodeclick: function (item) {
                exports.options.ParentId = item.id;
                $('.lr-search').trigger("click");
            }
        };
        //初始化
        $("#itemTree").treeview(item);

    };
    //加载Grid
    exports.loadGrid = function () {
        debugger
        var selectedRowIndex = 0;
        exports.options.$gridTable = $("#gridTable");
        exports.options.$gridTable.jqGrid({
            datatype: "json",
            url:  exports.path +"/queryMenuDataById",
            height: $.fn.getGridHeight(),
            autowidth: true,
            colModel: [
                { label: "菜单ID", name: "menuId", index: "menuId", hidden: true },
                { label: "是否子叶", name: "ifleaf", index: "ifleaf", hidden: true },
                { label: "菜单级别", name: "menuLevel", index: "menuLevel", hidden: true },
                { label: "菜单编码", name: "menuCode", index: "menuCode", width: 150, align: "left" },
                { label: "菜单名称", name: "menuName", index: "menuName", width: 150, align: "left" },
                { label: "菜单显示的名称", name: "menuLabel", index: "menuLabel", width: 350, align: "left" },
                { label: "菜单排序", name: "displayOrder", index: "displayOrder", width: 60, align: "center" },
                { label: "菜单功能路径", name: "functionPath", index: "functionPath", width: 100, align: "left" },
                { label: "功能描述", name: "functionDesc", index: "functionDesc", width: 100, align: "left" },
                { label: "所属应用类型", name: "appCode", index: "appCode", width: 100, align: "left" }
            ],

            shrinkToFit: false,
            altclass: 'altRowsColour',
            rowNum: "10",
            pager: '#pager2',
            caption:"菜单控制", //显示查询结果表格标题
            rownumbers: true,
            onSelectRow: function () {//行选中时
                selectedRowIndex = $("#" + this.menuId).getGridParam('selrow');
            },
            gridComplete: function () {
                $("#" + this.menuId).setSelection(selectedRowIndex, false);
            }
        });
    }
    //搜索
    exports.btnSearch=function() {

        exports.options.$gridTable.jqGrid('setGridParam', {
            url:  exports.path +  "/queryMenuDataById",
            postData: {
                parentId: exports.options.ParentId,
                conditionJson: $("#queryCondition").find('.dropdown-text').attr('data-value'),
                searchKeyWord: $("#txt_Keyword").val()
            },
        }).trigger('reloadGrid');
    }
    //删除
    exports.btnDelete= function () {
        var keyValue = $("#gridTable").jqGridRowValue("menuId");
        if (keyValue) {
            $.fn.deleteForm({
                url:  exports.path + "/delMenuDataById",
                param: { menuId: keyValue },
                success: function(data) {
                    $("#gridTable").resetSelection();
                    $("#gridTable").trigger("reloadGrid");
                }
            });
        } else {
            $.fn.modalMsg('请选择需要删除的数据项！', "warning");
        }
    }


    //编辑
    exports.btnEdit=function() {
        var keyValue = $("#gridTable").jqGridRowValue("menuId");
        if (checkedRow(keyValue)) {
            exports.options.KeyValue = keyValue;
            $.fn.modalOpen({
                id: "Form",
                title: '编辑功能',
                url: exports.path + '/addMenuForm?keyValue=' + keyValue,
                width: "700px",
                height: "430px",
                btn: null

            });
        }
    }
    //添加
    exports.btnAdd = function() {
        if (!exports.options.ParentId) {
            $.fn.modalMsg('请在左侧选择某一项！', "warning");
            return false;
        }
        var menuLevel = $("#gridTable").jqGridRowValue("menuLevel");
        if(menuLevel >= 2){
            $.fn.modalMsg('该级菜单不支持创建子节点', "warning");
            return false;
        }
        $.fn.modalOpen({
            id: "Form",
            title: '添加功能',
            url: exports.path +  '/addMenuForm?parentId=' + exports.options.ParentId,
            btn: null,
            width: "700px",
            height: "430px"

        });
    };
    /**********************表单************************/

    //加载表单
    exports.loadForm= function(readonly) {
        exports.options.KeyValue = $.fn.request('keyValue');
        exports.options.ParentId = $.fn.request('parentId');
        exports.options.ModuleId = $("#Id").val();

        $(function () {

            exports.initFormControl(readonly);
            exports.buttonOperation();
            exports.getButtonGrid();
            $('.selectmoduleicon').bind('click', function() {
                exports.btnModuleSelectIcon();
            });
            //为模块按钮管理注册事件
            $(".modulebtn").on("click", ".lr-add-button,.lr-edit-button,.lr-delete-button,.lr-copy-button", function () {
                var $this = $(this);
                if ($this.hasClass('lr-add-button')) {
                    exports.btnModuleBtnAdd();
                } else if ($this.hasClass('lr-edit-button')) {
                    exports.btnModuleBtnEdit();
                } else if ($this.hasClass('lr-delete-button')) {
                    exports.btnModuleBtnDelete();
                } else if ($this.hasClass('lr-copy-button')) {
                    exports.btnModuleBtnCopy();
                }
            });
        });
    }


    //初始化表单
    exports.initFormControl = function (readonly) {

        //上级
        $("#parentId").ComboBoxTree({
            url: exports.path +  "/queryMenuData",
            description: "==请选择==",
            height: "200px",
            allowSearch: true
        });

        //获取表单
        if (!!exports.options.KeyValue) {
            $.fn.setForm({
                url: exports.path +  "/getMenuDataInfo",
                param: { menuId: exports.options.KeyValue },
                success: function (data) {
                    $("#form1").SetWebControls(data);
                }
            });
        } else {
            $("#parentId").ComboBoxTreeSetValue(exports.options.ParentId);
        }
    }
    //保存表单
    exports.acceptClick = function () {
        if (!$('#form1').Validform()) {
            return false;
        }
        var postData = $("#form1").GetWebControls(exports.options.KeyValue);

        if (postData["parentId"] == "") {
            postData["parentId"] = 0;
        }
        postData["moduleButtonListJson"] = JSON.stringify(exports.options.ButtonJson);
        //  postData["moduleColumnListJson"] = JSON.stringify(exports.options.ColumnJson);
        $.fn.submitForm({
            url:  exports.path + "/addMenu",
            dataType:"json",
            param: postData,
            loading: "正在保存数据...",
            success: function() {
                $.currentIframe().$("#gridTable").trigger("reloadGrid");
            }
        });
    }
    /**
     * 按钮操作（取消，确定）
     * @returns {}
     */
    exports.buttonOperation=function() {
        var $last = $("#btn_last");
        var $finish = $("#btn_finish");

        $last.click(function(){
            $.fn.modalClose();
        })
        //完成提交保存
        $finish.click(function() {
            exports.acceptClick();
        });
    }

    /**
     * 获取按钮列表
     * @returns {}
     */
    exports.getButtonGrid = function () {
        var moduleId = exports.options.ModuleId;
        $.ajax({
            url: exports.path + "/content/supermgr/json/ModuleButtonList.json?moduleId=" + escape(moduleId),
            type: "get",
            dataType: "json",
            success: function (data) {
                exports.options.ButtonJson = data;
                window.ButtonJson = exports.options.ButtonJson;
            },
        });
        exports.options.$buttonGrid = $("#gridTable-button");
        exports.options.$buttonGrid.jqGrid({
            unwritten: false,
            url:  exports.path + "/content/supermgr/json/ModuleButtonTree.json?moduleId=" + escape(moduleId),
            datatype: "json",
            height: $(window).height() - 121,
            width: $(window).width() - 11,
            colModel: [
                { label: "主键", name: "Id", hidden: true },
                { label: "名称", name: "FullName", width: 140, align: "left", sortable: false },
                { label: "编号", name: "EnCode", width: 140, align: "left", sortable: false },
                { label: "地址", name: "ActionAddress", width: 500, align: "left", sortable: false },
            ],
            treeGrid: true,
            treeGridModel: "nested",
            ExpandColumn: "EnCode",
            rowNum: "all",
            rownumbers: true
        });

    }


    /**
     * 处理Json
     * @param {} buttonJson
     * @returns {}
     */
    exports.buttonListToListTreeJson= function (buttonJson) {
        $.ajax({
            url:  exports.path + "/SysMgr/ModuleMgr/ModuleButtonListToListTreeJson",
            data: { moduleButtonJson: JSON.stringify(buttonJson) },
            type: "post",
            dataType: "json",
            success: function (data) {
                exports.options.$buttonGrid.clearGridData();
                exports.options.$buttonGrid[0].addJSONData(data);
            },
        });
    }
    ///*************************按钮Form页***********************************************
    /**
     * 按钮Form加载
     * @returns {}
     */
    exports.buttonLoadForm = function () {
        exports.options.ParentId = $.fn.request('parentId');
        exports.options.ModuleButtonId = $.fn.request('moduleButtonId');
        exports.options.ModuleId = $.fn.request('moduleId');
        $(function() {
            exports.buttonInitControl();
        });
    }
    /**
     * 按钮
     * @returns {}
     */
    exports.buttonInitControl = function () {
        var dataJson = top.Form.ButtonJson;
        //上级
        $("#ParentId").ComboBoxTree({
            url: exports.path +  "/content/supermgr/json/ModuleButtonList.json",
            param: { moduleButtonJson: JSON.stringify(dataJson) },
            method: "post",
            description: "==请选择==",
            height: "150px"
        });
        if (!!exports.options.ModuleButtonId) {
            $.each(dataJson, function (i) {
                var row = dataJson[i];
                if (row.Id === exports.options.ModuleButtonId) {
                    $("#form1").SetWebControls(row);
                }
            });
        } else {
            $("#ModuleId").val(exports.options.ModuleId);
            if (!!exports.options.ParentId) {
                $("#ParentId").ComboBoxTreeSetValue(exports.options.ParentId);
            } else {
                $("#ParentId").ComboBoxTreeSetValue(0);
            }
        }
    }
    /**
     * 按钮保存表单
     * @returns {}
     */
    exports.buttonAcceptClick = function(callback) {
        if (!$('#form1').Validform()) {
            return false;
        }
        var data = $("#form1").GetWebControls(exports.options.ModuleButtonId);
        if (data["parentId"] == "") {
            data["parentId"] = 0;
        }
        if (data["ModuleId"] == "") {
            data["ModuleId"] = newGuid();
        }
        callback(data);
        $.fn.modalClose();

    };
    /**
     * 复制按钮操作Form
     * @returns {}
     */
    exports.buttonOptionModuleLoadForm = function () {
        exports.options.KeyValue = $.fn.request('keyValue');

        $(function() {
            exports.buttonGetModuleTree();
        });
    }
    /**
     * 加载功能模块树
     * @returns {}
     */
    exports.buttonGetModuleTree = function () {
        var item = {
            onnodeclick: function (item) {
                exports.options.ModuleId = item.id;
            },
            url: exports.path +  "/content/supermgr/json/ModuleButtonTree.json"
        };
        $("#ModuleTree").treeview(item);

    }

    return exports;
};
