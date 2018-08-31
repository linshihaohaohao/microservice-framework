package cn.com.bluemoon.domain.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_item_effect")
public class TbItemEffect {
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
     * 组成数组
     */
    @Column(name = "compositionIds")
    private String compositionIds;

    private Integer num;

    private String unit;

    @Column(name = "effectId")
    private String effectid;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "compare_name")
    private String compareName;

    @Column(name = "display_type")
    private String displayType;

    @Column(name = "display_compare_name")
    private String displayCompareName;

    @Column(name = "display_compare_sort")
    private Integer displayCompareSort;

    @Column(name = "display_compare")
    private Integer displayCompare;


    /**
     * 显示成分组成
     */
    @Column(name = "display_composition")
    private String displayComposition;

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
     * 获取组成数组
     *
     * @return compositionIds - 组成数组
     */
    public String getCompositionIds() {
        return compositionIds;
    }

    /**
     * 设置组成数组
     *
     * @param compositionIds 组成数组
     */
    public void setCompositionIds(String compositionIds) {
        this.compositionIds = compositionIds;
    }

    /**
     * @return num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @param num
     */
    public void setNum(Integer num) {
        this.num = num;
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
     * @return effectId
     */
    public String getEffectid() {
        return effectid;
    }

    /**
     * @param effectid
     */
    public void setEffectid(String effectid) {
        this.effectid = effectid;
    }

    /**
     * @return display_name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return compare_name
     */
    public String getCompareName() {
        return compareName;
    }

    /**
     * @param compareName
     */
    public void setCompareName(String compareName) {
        this.compareName = compareName;
    }

    /**
     * @return display_type
     */
    public String getDisplayType() {
        return displayType;
    }

    /**
     * @param displayType
     */
    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    /**
     * @return display_compare_name
     */
    public String getDisplayCompareName() {
        return displayCompareName;
    }

    /**
     * @param displayCompareName
     */
    public void setDisplayCompareName(String displayCompareName) {
        this.displayCompareName = displayCompareName;
    }

    /**
     * @return display_compare_sort
     */
    public Integer getDisplayCompareSort() {
        return displayCompareSort;
    }

    /**
     * @param displayCompareSort
     */
    public void setDisplayCompareSort(Integer displayCompareSort) {
        this.displayCompareSort = displayCompareSort;
    }

    /**
     * @return display_compare
     */
    public Integer getDisplayCompare() {
        return displayCompare;
    }

    /**
     * @param displayCompare
     */
    public void setDisplayCompare(Integer displayCompare) {
        this.displayCompare = displayCompare;
    }


    /**
     * 获取显示成分组成
     *
     * @return display_composition - 显示成分组成
     */
    public String getDisplayComposition() {
        return displayComposition;
    }

    /**
     * 设置显示成分组成
     *
     * @param displayComposition 显示成分组成
     */
    public void setDisplayComposition(String displayComposition) {
        this.displayComposition = displayComposition;
    }
}