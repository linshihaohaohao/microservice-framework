package cn.com.bluemoon.domain.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_item_safety")
public class TbItemSafety {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 0：删除（软删除）， 1：可用 , 2: 未激活
     */
    private Short status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String mid;

    /**
     * 名称
     */
    private String name;

    /**
     * 组成
     */
    @Column(name = "compositionIds")
    private String compositionids;

    /**
     * 星级
     */
    private Integer num;

    private String percent;

    private String unit;

    /**
     * 显示名称
     */
    @Column(name = "displayName")
    private String displayname;

    private String detail;

    /**
     * @return idx
     */
    public Long getIdx() {
        return idx;
    }

    /**
     * @param idx
     */
    public void setIdx(Long idx) {
        this.idx = idx;
    }

    /**
     * 获取版本号
     *
     * @return version - 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 获取0：删除（软删除）， 1：可用 , 2: 未激活
     *
     * @return status - 0：删除（软删除）， 1：可用 , 2: 未激活
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置0：删除（软删除）， 1：可用 , 2: 未激活
     *
     * @param status 0：删除（软删除）， 1：可用 , 2: 未激活
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取组成
     *
     * @return compositionIds - 组成
     */
    public String getCompositionids() {
        return compositionids;
    }

    /**
     * 设置组成
     *
     * @param compositionids 组成
     */
    public void setCompositionids(String compositionids) {
        this.compositionids = compositionids;
    }

    /**
     * 获取星级
     *
     * @return num - 星级
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置星级
     *
     * @param num 星级
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * @return percent
     */
    public String getPercent() {
        return percent;
    }

    /**
     * @param percent
     */
    public void setPercent(String percent) {
        this.percent = percent;
    }

    /**
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取显示名称
     *
     * @return displayName - 显示名称
     */
    public String getDisplayname() {
        return displayname;
    }

    /**
     * 设置显示名称
     *
     * @param displayname 显示名称
     */
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    /**
     * @return desc
     */
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}