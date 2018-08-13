package cn.com.bluemoon.demo.user.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "demo_user")
public class DemoUser {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 有效性标注
     */
    @Column(name = "valid_flag")
    private Integer validFlag;

    /**
     * 注册时间
     */
    @Column(name = "sign_in_time")
    private Date signInTime;

    /**
     * 介绍
     */
    private String introduction;

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
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取登录密码
     *
     * @return password - 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取有效性标注
     *
     * @return valid_flag - 有效性标注
     */
    public Integer getValidFlag() {
        return validFlag;
    }

    /**
     * 设置有效性标注
     *
     * @param validFlag 有效性标注
     */
    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }

    /**
     * 获取注册时间
     *
     * @return sign_in_time - 注册时间
     */
    public Date getSignInTime() {
        return signInTime;
    }

    /**
     * 设置注册时间
     *
     * @param signInTime 注册时间
     */
    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    /**
     * 获取介绍
     *
     * @return introduction - 介绍
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置介绍
     *
     * @param introduction 介绍
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}