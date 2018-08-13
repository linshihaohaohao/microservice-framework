package cn.com.bluemoon.admin.domain.model;

import javax.persistence.*;

@Table(name = "admin_sys_dict_type")
public class AdminSysDictType {
    /**
     * 业务字典编码
     */
    @Id
    @Column(name = "dict_type_code")
    private String dictTypeCode;

    /**
     * 业务字典名称
     */
    @Column(name = "dict_type_bane")
    private String dictTypeBane;

    /**
     * 获取业务字典编码
     *
     * @return dict_type_code - 业务字典编码
     */
    public String getDictTypeCode() {
        return dictTypeCode;
    }

    /**
     * 设置业务字典编码
     *
     * @param dictTypeCode 业务字典编码
     */
    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    /**
     * 获取业务字典名称
     *
     * @return dict_type_bane - 业务字典名称
     */
    public String getDictTypeBane() {
        return dictTypeBane;
    }

    /**
     * 设置业务字典名称
     *
     * @param dictTypeBane 业务字典名称
     */
    public void setDictTypeBane(String dictTypeBane) {
        this.dictTypeBane = dictTypeBane;
    }
}