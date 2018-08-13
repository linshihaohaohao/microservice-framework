package cn.com.bluemoon.admin.interceptor;
  
import javax.servlet.jsp.tagext.BodyTagSupport;  
  

public class AuthorizeTag extends BodyTagSupport {  
  
    private static final long serialVersionUID = 1L;  
  
    private String URL;  
  
    public String getURL() {  
        return URL;  
    }  
      
    public void setURL(String uRL) {  
        URL = uRL;  
    }  
    @Override  
    public int doStartTag() {  
        // 如果URL不空就显示URL，否则就不显  
        if (null != URL && !"".equals(URL)) {  
            return EVAL_BODY_INCLUDE;  
        }  
        return this.SKIP_BODY;  
    }  
}  