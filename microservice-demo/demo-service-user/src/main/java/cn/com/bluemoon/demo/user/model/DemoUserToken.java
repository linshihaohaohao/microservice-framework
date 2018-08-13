package cn.com.bluemoon.demo.user.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Table(name = "demo_user_token")
public class DemoUserToken implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7676279975269927296L;

	/**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * token
     */
    private String token;

    /**
     * 生成时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 有效标识
     */
    @Column(name = "valid_flag")
    private Integer validFlag;

    /**
     * 有效性最后刷新时间
     */
    @Column(name = "last_refresh_time")
    private Date lastRefreshTime;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取token
     *
     * @return token - token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取生成时间
     *
     * @return create_time - 生成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置生成时间
     *
     * @param createTime 生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取有效标识
     *
     * @return valid_flag - 有效标识
     */
    public Integer getValidFlag() {
        return validFlag;
    }

    /**
     * 设置有效标识
     *
     * @param validFlag 有效标识
     */
    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }

    /**
     * 获取有效性最后刷新时间
     *
     * @return last_refresh_time - 有效性最后刷新时间
     */
    public Date getLastRefreshTime() {
        return lastRefreshTime;
    }

    /**
     * 设置有效性最后刷新时间
     *
     * @param lastRefreshTime 有效性最后刷新时间
     */
    public void setLastRefreshTime(Date lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }
}