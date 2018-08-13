package cn.com.bluemoon.admin.domain.vo;

import java.util.Date;

public class MallSysUserDelegRoleVo extends MallSysUserDelegRoleKeyVo {
    private String roleCode;

    private String createUser;

    private Date createTime;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}