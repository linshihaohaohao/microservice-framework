package cn.com.bluemoon.admin.domain.model;

import javax.persistence.*;

@Table(name = "admin_sys_dict_entry")
public class AdminSysDictEntry {
    /**
     * 业务字典子选项
     */
    @Id
    @Column(name = "dict_type_code")
    private String dictTypeCode;

    @Id
    @Column(name = "dict_code")
    private String dictCode;

    /**
     * 业务字典子选项名称
     */
    @Column(name = "dict_name")
    private String dictName;

    /**
     * 状态（1使用中/0已废弃）
     */
    private Integer status;

    /**
     * 排序编码
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 获取业务字典子选项
     *
     * @return dict_type_code - 业务字典子选项
     */
    public String getDictTypeCode() {
        return dictTypeCode;
    }

    /**
     * 设置业务字典子选项
     *
     * @param dictTypeCode 业务字典子选项
     */
    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    /**
     * @return dict_code
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * @param dictCode
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 获取业务字典子选项名称
     *
     * @return dict_name - 业务字典子选项名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 设置业务字典子选项名称
     *
     * @param dictName 业务字典子选项名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取状态（1使用中/0已废弃）
     *
     * @return status - 状态（1使用中/0已废弃）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（1使用中/0已废弃）
     *
     * @param status 状态（1使用中/0已废弃）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取排序编码
     *
     * @return sort_no - 排序编码
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序编码
     *
     * @param sortNo 排序编码
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}