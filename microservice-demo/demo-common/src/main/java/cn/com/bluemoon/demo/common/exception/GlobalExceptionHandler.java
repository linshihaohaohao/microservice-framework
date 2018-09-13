package cn.com.bluemoon.demo.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.bluemoon.demo.common.domain.BaseResponse;

import com.fasterxml.jackson.core.JsonParseException;

import net.sf.json.JSONException;

/**
 * 全局异常处理
 * @author linshihao
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LogManager.getLogger(getClass());

	@ExceptionHandler(value = AssertException.class)
    @ResponseBody
    public BaseResponse BootExceptionHandler(HttpServletRequest req, AssertException e) {
		BaseResponse response = new BaseResponse();
    	response.setIsSuccess(false);
    	response.setResponseCode(e.getCode());
    	response.setResponseMsg(e.getMessage());
    	logger.error(e.getCode(),e);
    	logger.warn("ExceptionHandler:"+response.toString());
        return response;
    }
	
	@ExceptionHandler(value = JSONException.class)
	@ResponseBody
	public BaseResponse JSONExceptionHandler(HttpServletRequest req, JSONException e) {		
		BaseResponse response = new BaseResponse();
		response.setIsSuccess(false);
		response.setResponseCode(1102);
		response.setResponseMsg("请求参数格式异常");
		logger.error("1102",e);
		logger.error("ExceptionHandler"+response.toString());
		return response;
	}
	
	@ExceptionHandler(value = JsonParseException.class)
	@ResponseBody
	public BaseResponse JsonParseExceptionHandler(HttpServletRequest req, JsonParseException e) {
		BaseResponse response = new BaseResponse();
		response.setIsSuccess(false);
		response.setResponseCode(1102);
		response.setResponseMsg("请求参数格式异常");
		logger.error("1102",e);
		logger.error("ExceptionHandler"+response.toString());
		return response;
	}
    
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse ExceptionHandler(HttpServletRequest req, Exception e) {   	
    	BaseResponse response = new BaseResponse();
		response.setIsSuccess(false);
		response.setResponseCode(1000);
		response.setResponseMsg("服务器正在繁忙，请稍后再试哦~");
		logger.error("1000",e);
    	logger.error("ExceptionHandler"+response.toString());
    	return response;
    }

}
