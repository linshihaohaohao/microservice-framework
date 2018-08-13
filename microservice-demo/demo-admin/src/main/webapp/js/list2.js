var list = {
    root :"",

    init :function (path) {
        list.root = path;
        list.initEvent();
    },
    init1 :function (path) {
        list.root =path;
        list.initEvent1();
    },

    initEvent :function(){
        //1.初始化Table
        list.tableInit();
        //初始化按钮
       list.buttonInit(list.root);
       console.log("2222")
    },
    initEvent1 :function(){
        console.log(list.root)
        list.initFormControl(true);
    },
    tableInit :function () {
        console.log(list.root )
        var url = list.root + "/toListJson?key=" +Math.random();
        $('#tb_departments').bootstrapTable({
            url: url, //请求后台的URL（*）
            method: 'post', //请求方式（*）
            toolbar: '#toolbar', //工具按钮用哪个容器
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            queryParams: list.queryParams,//传递参数（*）
            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
            pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true, //是否显示所有的列
            showRefresh: true, //是否显示刷新按钮
            minimumCountColumns: 2, //最少允许的列数
            clickToSelect: true, //是否启用点击选中行
            height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID", //每一行的唯一标识，一般为主键列
            showToggle:true, //是否显示详细视图和列表视图的切换按钮
            cardView: false, //是否显示详细视图
            detailView: false, //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: 'id'
            }, {
                field: 'lastname',
                title: '姓'
            }, {
                field: 'username',
                title: '用户名'
            }, {
                field: 'state',
                title: '状态'
            }, ]
        });
    },
   /* {
   如果是图片的写法
     field: 'thumb_img',
     title: '主图',
     align: 'center',
     formatter:function (value,row,index) {
     return '<img  src='+value+' width=50 class="img-rounded" >';
     }
     }  */
    queryParams : function (params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, //页面大小
            offset: params.offset, //页码
            departmentname: $("#txt_search_departmentname").val(),
            statu: $("#txt_search_statu").val()
        };
        return temp;
    },
    buttonInit : function(path){
        $(".titlePanel").on("click", ".lr-add,.lr-edit,.lr-delete", function () {
            var $this = $(this);
            if ($this.hasClass('lr-add')) {
                list.btnAdd(path);
            }
            else if ($this.hasClass('lr-edit')) {
                list.btnEdit();
            }
            else if ($this.hasClass("lr-delete")) {
                list.btnDelete();
            };
        });

        $("#btn_query").click(function () {
            // 很重要的一步，刷新url！
            $('#tb_departments').bootstrapTable(('refresh'));
            //解决查询分页的问题
            $('#tb_departments').bootstrapTable({pageNumber:1,pageSize:10});

            list.tableInit();
        })
    },
    btnAdd : function (path) {
        $.fn.modalOpen({
            id: "addForm",
            title: '添加',
            url: path + '/addForm',
            width: "750px",
            height: "550px",
            callBack: function (iframeId) {
                list.acceptClick();
            }
        });
    },
    acceptClick : function() {
        if (!$('#form1').Validform()) {
            return false;
        }
        var postData = $("#form1").GetWebControls(list.options.KeyValue);

        $.fn.submitForm({
            url: "/SysMgr/SchedulerMgr/Save?keyValue=" + list.options.KeyValue,
            param: postData,
            loading: "正在保存数据...",
            success: function() {
                $.currentIframe().$("#gridTable").resetSelection();
                $.currentIframe().$("#gridTable").trigger("reloadGrid");
            }
        });
    },
    btnDelete : function () {
        var a= $("#tb_departments").bootstrapTable('getSelections');
        var b = "";
        if (a.length == 1) {
            b = a[0].id ;
            if (checkedRow(b)) {
                $.fn.confirmAjax({
                    msg: "注：您确定要【删除】该定时任务么？该删除操作会级联删除任务日志，请谨慎操作！",
                    url: "/SysMgr/SchedulerMgr/DeleteJobStatus",
                    param: { keyValue: b },
                    success: function (data) {
                        $("#gridTable").trigger("reloadGrid");
                    }
                });
            }
        } else {
            $.fn.modalMsg("请选中一行", "warning");
        }
    },
    btnEdit : function () {
        var a= $("#tb_departments").bootstrapTable('getSelections');
        var b = "";
        if (a.length == 1) {
           b = a[0].id ;
            if (checkedRow(b)) {
                $.fn.modalOpen({
                    id: "jobDetailForm",
                    title: '编辑【' + b + '】任务',
                    url: '/pages/supermgr/JobDetailForm.html?keyValue=' + b,
                    width: "750px",
                    height: "550px",
                    callBack: function (iframeId) {
                        top.frames[iframeId].AcceptClick();
                    }
                });
            }
        } else {
            $.fn.modalMsg("请选中一行", "warning");
        }
    },

    initFormControl : function (readonly) {
        // list.options.KeyValue = $.fn.request("keyValue");
        //获取表单
        if(false){
            $.fn.setForm({
                url: list.root + "/SysMgr/SchedulerMgr/GetJobLogEntity",
                param: { keyValue: "ddd" },
                success: function (data) {
                    $("#form1").SetWebControls(data);

                    if (readonly) {
                        $("#form1").find('.form-control,.ui-select,input,textarea').attr('disabled', 'disabled');
                    }
                }
            });
        }

    }
}
