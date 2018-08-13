$(function () {
    $("#startTime").datetimepicker({
        language: 'zh-CN',
        autoclose: true,
        todayBtn: true,
        format: "yyyy-mm-dd hh:ii", //选择日期后，文本框显示的日期格式
        pickerPosition: ("bottom-left")
    }).on('changeDate', function (e) {
        $('#endTime').datetimepicker('setStartDate', e.date);
    });

    $('#startTime').datetimepicker('setStartDate', new Date());

    $("#endTime").datetimepicker({
        language: 'zh-CN',
        autoclose: true,
        todayBtn: true,
        format: "yyyy-mm-dd hh:ii", //选择日期后，文本框显示的日期格式
        pickerPosition: ("bottom-left")
    }).on('changeDate', function (e) {
        $('#startTime').datetimepicker('setEndDate', e.date);
    });

    $.fn.setForm = function(jsonValue) {
        var obj = this;
        $.each(jsonValue, function (name, ival) {
            var $oinput = obj.find("[name=" + name + "]");
            if ($oinput.attr("type") == "radio" || $oinput.attr("type") == "checkbox") {
                $oinput.each(function() {
                    if (Object.prototype.toString.apply(ival) == '[object Array]') { //是复选框，并且是数组
                        for (var i = 0; i < ival.length; i++) {
                            if ($(this).val() == ival[i]) //或者文本相等
                                $(this).prop("checked", true);
                        }
                    } else {
                        if ($(this).val() == ival)
                            $(this).prop("checked", true);
                    }
                });
            } else if ($oinput.attr("type") == "textarea") { //多行文本框
                obj.find("[name=" + name + "]").html(ival);
            } else {
                obj.find("[name=" + name + "]").val(ival);
                if(ival.time != null){
                    var timeStr = new Date(ival.time).format("yyyy-MM-dd hh:mm");
                    obj.find("[name=" + name + "]").val(timeStr);
                }
            }
        });
    }

    $('#endTime').datetimepicker('setStartDate', new Date());
})

/**
 * 日期联动
 * @param $startTime 开始日期对象
 * @param $endTime 结束日期对象
 * @param timeFormat 时间格式
 * @param minview 最精确的时间选择视图
 * @param maxview 最高能展示的选择范围视图
 * @param startview 首先显示的视图
 */
function initDateTimePicker($startTime, $endTime, timeFormat, minview, maxview, startview) {
    $startTime.datetimepicker("remove");
    $startTime.datetimepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true,
//            startDate: new Date(),
        endDate: $("#endTimeShow").val(),
        format: timeFormat,
        startView: startview,
        minView: minview,
        maxView: maxview
    }).on("changeDate", function () {
        $endTime.datetimepicker("remove");
        $endTime.datetimepicker({
            language: 'zh-CN',
            autoclose: true,
            todayHighlight: true,
            startDate: $("#startTimeShow").val(),
            format: timeFormat,
            startView: startview,
            minView: minview,
            maxView: maxview
        })
    });
    $endTime.datetimepicker("remove");
    $endTime.datetimepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true,
        startDate: $("#startTimeShow").val(),
        format: timeFormat,
        startView: startview,
        minView: minview,
        maxView: maxview
    }).on("changeDate", function () {
        $(startTime).datetimepicker("remove");
        $(startTime).datetimepicker({
            language: 'zh-CN',
            autoclose: true,
            todayHighlight: true,
            endDate: $("#endTimeShow").val(),
//                startDate: new Date(),
            format: timeFormat,
            startView: startview,
            minView: minview,
            maxView: maxview
        })
    });
}

function dateFormat(value) {
    try {
        var k = parseInt(value["time"]);
        if (k < 0)
            return null;

        var date = new Date(value["time"]);
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        // var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
//            var milliseconds = date.getMilliseconds();
        return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes;
    } catch (ex) {
        return "";
    }
}
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()
        //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

function comptime(beginTime, endTime) {
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');

    beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);
    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
        return 1;
    } else if (a > 0) {
        return 2;
    } else if (a == 0) {
        return 0;
    } else {
        return -1;
    }
}


