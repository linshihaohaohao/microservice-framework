package cn.com.bluemoon.admin.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.bluemoon.admin.domain.model.AdminSysLogInfo;
import cn.com.bluemoon.admin.domain.vo.UserInfo;
import cn.com.bluemoon.admin.service.LogService;
import cn.com.bluemoon.admin.utils.IpUtil;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter
		implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Autowired
	private LogService logService;

	/**
	 * 登录url
	 */
	private String tipsUrl;

	public String getTipsUrl() {
		return tipsUrl;
	}

	public void setTipsUrl(String tipsUrl) {
		this.tipsUrl = tipsUrl;
	}

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>(
			"StopWatch-StartTime");

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("------preHandle------");
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		// 获取session
		HttpSession session = request.getSession(true);
		// 判断用户是否存在，不存在就跳转到登录界面
		if (session.getAttribute("user") == null) {
			logger.info("------:跳转到login页面！");
			response.sendRedirect(request.getContextPath() + "/toLogin?isPre=1");
			return false;
		} else {
			session.setAttribute("user", session.getAttribute("user"));
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// System.out.println(">>>AuthenticationInterceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		Long consumeTime = endTime - beginTime;// 3、消耗的时间

		HttpSession session = request.getSession(true);
		UserInfo user = (UserInfo) session.getAttribute("user");
		String userIp = IpUtil.getIpAddr(request);

		// String desc = String.format("%s consume %d millis",
		// request.getRequestURI(), consumeTime);

		AdminSysLogInfo logInfo = new AdminSysLogInfo();
		logInfo.setOperTime(new Date(beginTime));
		logInfo.setUserId(user.getAccount());
		logInfo.setUserName(user.getRealName());
		logInfo.setUserIp(userIp);
		logInfo.setBroswer(request.getHeader("user-agent"));
		logInfo.setRequestUri(request.getRequestURI());
		logInfo.setTimeConsume(consumeTime.intValue());
		logInfo.setMethod(request.getMethod());
		logInfo.setParams(request.getParameterMap());
		// logInfo.setDescription(desc);

		if (null != ex) {
			StringWriter stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			String exceptionStr = stringWriter.toString();
			logInfo.setExceptionStr(exceptionStr);
		}

		logService.saveSysOperateLog(logInfo);
	}

	// 获取request请求body中参数
	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return str;
	}
}
