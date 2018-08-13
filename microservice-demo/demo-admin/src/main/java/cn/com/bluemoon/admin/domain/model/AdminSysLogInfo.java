package cn.com.bluemoon.admin.domain.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import cn.com.bluemoon.utils.string.StringUtil;

@Table(name = "admin_sys_log_info")
public class AdminSysLogInfo {
    /**
     * 日志ID
     */
    @Id
    @Column(name = "log_id")
    private Long logId;

    /**
     * 操作时间
     */
    @Column(name = "oper_time")
    private Date operTime;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 来源IP
     */
    @Column(name = "user_ip")
    private String userIp;

    /**
     * 浏览器类型
     */
    private String broswer;

    /**
     * 请求URI
     */
    @Column(name = "request_uri")
    private String requestUri;

    /**
     * 执行时间（ms）
     */
    @Column(name = "time_consume")
    private Integer timeConsume;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作结果
     */
    private String description;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 异常信息
     */
    @Column(name = "exception_str")
    private String exceptionStr;

    /**
     * 获取日志ID
     *
     * @return log_id - 日志ID
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * 设置日志ID
     *
     * @param logId 日志ID
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * 获取操作时间
     *
     * @return oper_time - 操作时间
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * 设置操作时间
     *
     * @param operTime 操作时间
     */
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取来源IP
     *
     * @return user_ip - 来源IP
     */
    public String getUserIp() {
        return userIp;
    }

    /**
     * 设置来源IP
     *
     * @param userIp 来源IP
     */
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    /**
     * 获取浏览器类型
     *
     * @return broswer - 浏览器类型
     */
    public String getBroswer() {
        return broswer;
    }

    /**
     * 设置浏览器类型
     *
     * @param broswer 浏览器类型
     */
    public void setBroswer(String broswer) {
        this.broswer = broswer;
    }

    /**
     * 获取请求URI
     *
     * @return request_uri - 请求URI
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * 设置请求URI
     *
     * @param requestUri 请求URI
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * 获取执行时间（ms）
     *
     * @return time_consume - 执行时间（ms）
     */
    public Integer getTimeConsume() {
        return timeConsume;
    }

    /**
     * 设置执行时间（ms）
     *
     * @param timeConsume 执行时间（ms）
     */
    public void setTimeConsume(Integer timeConsume) {
        this.timeConsume = timeConsume;
    }

    /**
     * 获取操作方式
     *
     * @return method - 操作方式
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置操作方式
     *
     * @param method 操作方式
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取操作结果
     *
     * @return description - 操作结果
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置操作结果
     *
     * @param description 操作结果
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取操作提交的数据
     *
     * @return params - 操作提交的数据
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置操作提交的数据
     *
     * @param params 操作提交的数据
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取异常信息
     *
     * @return exception_str - 异常信息
     */
    public String getExceptionStr() {
        return exceptionStr;
    }

    /**
     * 设置异常信息
     *
     * @param exceptionStr 异常信息
     */
    public void setExceptionStr(String exceptionStr) {
        this.exceptionStr = exceptionStr;
    }
    
    /**
	 * 设置请求参数
	 * @param paramMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParams(Map paramMap){
		if (paramMap == null){
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(StringUtil.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
		}
		this.params = params.toString();
	}
}