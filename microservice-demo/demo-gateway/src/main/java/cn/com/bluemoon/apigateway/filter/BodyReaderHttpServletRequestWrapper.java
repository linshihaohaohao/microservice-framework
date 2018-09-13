package cn.com.bluemoon.apigateway.filter;


import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
  
/**
 * @author linshihao
 * @version 1.0
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {  
  
    private final byte[] body;  
      
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {  
        super(request);  
        body = getRequestPostBytes(request);  
    }  
  
    @Override  
    public BufferedReader getReader() throws IOException {  
        return new BufferedReader(new InputStreamReader(getInputStream()));  
    }  
  
    @Override  
    public ServletInputStream getInputStream() throws IOException {  
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);  
        
        return new ServletInputStream() {  
            @Override  
            public int read() throws IOException {  
                return bais.read();  
            }

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
			}  
        };  
    }  
  
    /**      
     * 描述:获取 post 请求的 byte[] 数组
     * @param request
     * @return
     * @throws IOException      
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readlen = request.getInputStream().read(buffer, i,contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }
    
    /**
     * 描述:获取 post 请求的json字符串
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        if(buffer==null){
        	return null;
        }
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
    
} 