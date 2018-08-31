package cn.com.bluemoon.domain.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_item_composition")
public class TbItemComposition {
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

    @Column(name = "bevol_id")
    private Integer bevolId;

    @Column(name = "bevol_src_id")
    private Integer bevolSrcId;

    /**
     * 名称
     */
    private String title;

    private String english;

    private String efficacy;

    private Integer frequency;

    /**
     * 活性成分
     */
    private String active;

    /**
     * 致痘风险
     */
    @Column(name = "acne_risk")
    private String acneRisk;

    private String cas;

    @Column(name = "other_title")
    private String otherTitle;

    private String remark;

    @Column(name = "shen_yong")
    private String shenYong;

    @Column(name = "used_num")
    private String usedNum;

    private String used;

    /**
     * 使用目的 json组成
     */
    private Object useds;

    @Column(name = "cur_used_name")
    private String curUsedName;

    /**
     * 安全风险
     */
    private String safety;

    /**
     * 使用目的 便于显示
     */
    @Column(name = "used_title")
    private String usedTitle;

    @Column(name = "cm_title")
    private String cmTitle;

    @Column(name = "cm_english")
    private String cmEnglish;

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
     * @return bevol_id
     */
    public Integer getBevolId() {
        return bevolId;
    }

    /**
     * @param bevolId
     */
    public void setBevolId(Integer bevolId) {
        this.bevolId = bevolId;
    }

    /**
     * @return bevol_src_id
     */
    public Integer getBevolSrcId() {
        return bevolSrcId;
    }

    /**
     * @param bevolSrcId
     */
    public void setBevolSrcId(Integer bevolSrcId) {
        this.bevolSrcId = bevolSrcId;
    }

    /**
     * 获取名称
     *
     * @return title - 名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置名称
     *
     * @param title 名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return english
     */
    public String getEnglish() {
        return english;
    }

    /**
     * @param english
     */
    public void setEnglish(String english) {
        this.english = english;
    }

    /**
     * @return efficacy
     */
    public String getEfficacy() {
        return efficacy;
    }

    /**
     * @param efficacy
     */
    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    /**
     * @return frequency
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * @param frequency
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    /**
     * 获取活性成分
     *
     * @return active - 活性成分
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置活性成分
     *
     * @param active 活性成分
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取致痘风险
     *
     * @return acne_risk - 致痘风险
     */
    public String getAcneRisk() {
        return acneRisk;
    }

    /**
     * 设置致痘风险
     *
     * @param acneRisk 致痘风险
     */
    public void setAcneRisk(String acneRisk) {
        this.acneRisk = acneRisk;
    }

    /**
     * @return cas
     */
    public String getCas() {
        return cas;
    }

    /**
     * @param cas
     */
    public void setCas(String cas) {
        this.cas = cas;
    }

    /**
     * @return other_title
     */
    public String getOtherTitle() {
        return otherTitle;
    }

    /**
     * @param otherTitle
     */
    public void setOtherTitle(String otherTitle) {
        this.otherTitle = otherTitle;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return shen_yong
     */
    public String getShenYong() {
        return shenYong;
    }

    /**
     * @param shenYong
     */
    public void setShenYong(String shenYong) {
        this.shenYong = shenYong;
    }

    /**
     * @return used_num
     */
    public String getUsedNum() {
        return usedNum;
    }

    /**
     * @param usedNum
     */
    public void setUsedNum(String usedNum) {
        this.usedNum = usedNum;
    }

    /**
     * @return used
     */
    public String getUsed() {
        return used;
    }

    /**
     * @param used
     */
    public void setUsed(String used) {
        this.used = used;
    }

    /**
     * 获取使用目的 json组成
     *
     * @return useds - 使用目的 json组成
     */
    public Object getUseds() {
        return useds;
    }

    /**
     * 设置使用目的 json组成
     *
     * @param useds 使用目的 json组成
     */
    public void setUseds(Object useds) {
        this.useds = useds;
    }

    /**
     * @return cur_used_name
     */
    public String getCurUsedName() {
        return curUsedName;
    }

    /**
     * @param curUsedName
     */
    public void setCurUsedName(String curUsedName) {
        this.curUsedName = curUsedName;
    }

    /**
     * 获取安全风险
     *
     * @return safety - 安全风险
     */
    public String getSafety() {
        return safety;
    }

    /**
     * 设置安全风险
     *
     * @param safety 安全风险
     */
    public void setSafety(String safety) {
        this.safety = safety;
    }

    /**
     * 获取使用目的 便于显示
     *
     * @return used_title - 使用目的 便于显示
     */
    public String getUsedTitle() {
        return usedTitle;
    }

    /**
     * 设置使用目的 便于显示
     *
     * @param usedTitle 使用目的 便于显示
     */
    public void setUsedTitle(String usedTitle) {
        this.usedTitle = usedTitle;
    }

    /**
     * @return cm_title
     */
    public String getCmTitle() {
        return cmTitle;
    }

    /**
     * @param cmTitle
     */
    public void setCmTitle(String cmTitle) {
        this.cmTitle = cmTitle;
    }

    /**
     * @return cm_english
     */
    public String getCmEnglish() {
        return cmEnglish;
    }

    /**
     * @param cmEnglish
     */
    public void setCmEnglish(String cmEnglish) {
        this.cmEnglish = cmEnglish;
    }
}