<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" charset="utf-8" src="${root}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${root}/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${root}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
  UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
  UE.Editor.prototype.getActionUrl = function(action) {
    if (action == "uploadimage" || action == "uploadscrawl" || action == "listimage" || action == "catchimage") {
//      return "http://tmallapi.bluemoon.com.cn/fileHttp2/chunk/upload";
        return "${root}/upload/ue"
    } else {
      return this._bkGetActionUrl.call(this, action);
    }
  }
</script>

