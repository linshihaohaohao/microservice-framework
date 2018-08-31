package cn.com.bluemoon.domain.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_bevol_item")
public class TbBevolItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /**
     * mid,搜索主键
     */
    private String mid;

    /**
     * 产品标题
     */
    private String title;

    /**
     * 产地
     */
    private String alias;

    /**
     * 类型排序
     */
    @Column(name = "c_sort")
    private String cSort;

    /**
     * index_name
     */
    @Column(name = "index_name")
    private String indexName;

    /**
     * 点击数
     */
    @Column(name = "hit_num")
    private Integer hitNum;

    /**
     * 备案号
     */
    private String approval;

    /**
     * brand_id
     */
    @Column(name = "brand_id")
    private String brandId;

    /**
     * tstamap
     */
    private String tstamap;

    private String grade;

    private String category;

    @Column(name = "alias_2")
    private String alias2;

    private String remark3;

    @Column(name = "comment_num")
    private Integer commentNum;

    @Column(name = "hidden_skin")
    private String hiddenSkin;

    /**
     * 安全星级
     */
    @Column(name = "safety_1_num")
    private String safety1Num;

    @Column(name = "cps_search")
    private String cpsSearch;

    private String remark;

    /**
     * 图片地址
     */
    @Column(name = "image_src")
    private String imageSrc;

    /**
     * companyEnglish
     */
    @Column(name = "company_english")
    private String companyEnglish;

    /**
     * 公司
     */
    private String company;

    private Integer price;

    private String country;

    @Column(name = "sellPrice")
    private Integer sellprice;

    @Column(name = "approval_date")
    private Date approvalDate;

    @Column(name = "data_type_str")
    private String dataTypeStr;

    @Transient
    private Object goodsExt;
    @Transient
    private Object doyen;

    public Object getDoyen() {
        return doyen;
    }

    public void setDoyen(Object doyen) {
        this.doyen = doyen;
    }

    public Object getGoodsExt() {
        return goodsExt;
    }

    public void setGoodsExt(Object goodsExt) {
        this.goodsExt = goodsExt;
    }

    /**
     * 质量
     */
    private String capacity;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
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
     * 获取mid,搜索主键
     *
     * @return mid - mid,搜索主键
     */
    public String getMid() {
        return mid;
    }

    /**
     * 设置mid,搜索主键
     *
     * @param mid mid,搜索主键
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * 获取产品标题
     *
     * @return title - 产品标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置产品标题
     *
     * @param title 产品标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取产地
     *
     * @return alias - 产地
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置产地
     *
     * @param alias 产地
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 获取类型排序
     *
     * @return c_sort - 类型排序
     */
    public String getcSort() {
        return cSort;
    }

    /**
     * 设置类型排序
     *
     * @param cSort 类型排序
     */
    public void setcSort(String cSort) {
        this.cSort = cSort;
    }

    /**
     * 获取index_name
     *
     * @return index_name - index_name
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * 设置index_name
     *
     * @param indexName index_name
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * 获取点击数
     *
     * @return hit_num - 点击数
     */
    public Integer getHitNum() {
        return hitNum;
    }

    /**
     * 设置点击数
     *
     * @param hitNum 点击数
     */
    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    /**
     * 获取备案号
     *
     * @return approval - 备案号
     */
    public String getApproval() {
        return approval;
    }

    /**
     * 设置备案号
     *
     * @param approval 备案号
     */
    public void setApproval(String approval) {
        this.approval = approval;
    }

    /**
     * 获取brand_id
     *
     * @return brand_id - brand_id
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * 设置brand_id
     *
     * @param brandId brand_id
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取tstamap
     *
     * @return tstamap - tstamap
     */
    public String getTstamap() {
        return tstamap;
    }

    /**
     * 设置tstamap
     *
     * @param tstamap tstamap
     */
    public void setTstamap(String tstamap) {
        this.tstamap = tstamap;
    }

    /**
     * @return grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return alias_2
     */
    public String getAlias2() {
        return alias2;
    }

    /**
     * @param alias2
     */
    public void setAlias2(String alias2) {
        this.alias2 = alias2;
    }

    /**
     * @return remark3
     */
    public String getRemark3() {
        return remark3;
    }

    /**
     * @param remark3
     */
    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    /**
     * @return comment_num
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * @param commentNum
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * @return hidden_skin
     */
    public String getHiddenSkin() {
        return hiddenSkin;
    }

    /**
     * @param hiddenSkin
     */
    public void setHiddenSkin(String hiddenSkin) {
        this.hiddenSkin = hiddenSkin;
    }

    /**
     * 获取安全星级
     *
     * @return safety_1_num - 安全星级
     */
    public String getSafety1Num() {
        return safety1Num;
    }

    /**
     * 设置安全星级
     *
     * @param safety1Num 安全星级
     */
    public void setSafety1Num(String safety1Num) {
        this.safety1Num = safety1Num;
    }

    /**
     * @return cps_search
     */
    public String getCpsSearch() {
        return cpsSearch;
    }

    /**
     * @param cpsSearch
     */
    public void setCpsSearch(String cpsSearch) {
        this.cpsSearch = cpsSearch;
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
     * 获取图片地址
     *
     * @return image_src - 图片地址
     */
    public String getImageSrc() {
        return imageSrc;
    }

    /**
     * 设置图片地址
     *
     * @param imageSrc 图片地址
     */
    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    /**
     * 获取companyEnglish
     *
     * @return company_english - companyEnglish
     */
    public String getCompanyEnglish() {
        return companyEnglish;
    }

    /**
     * 设置companyEnglish
     *
     * @param companyEnglish companyEnglish
     */
    public void setCompanyEnglish(String companyEnglish) {
        this.companyEnglish = companyEnglish;
    }

    /**
     * 获取公司
     *
     * @return company - 公司
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置公司
     *
     * @param company 公司
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return sellPrice
     */
    public Integer getSellprice() {
        return sellprice;
    }

    /**
     * @param sellprice
     */
    public void setSellprice(Integer sellprice) {
        this.sellprice = sellprice;
    }

    /**
     * @return approval_date
     */
    public Date getApprovalDate() {
        return approvalDate;
    }

    /**
     * @param approvalDate
     */
    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    /**
     * @return data_type_str
     */
    public String getDataTypeStr() {
        return dataTypeStr;
    }

    /**
     * @param dataTypeStr
     */
    public void setDataTypeStr(String dataTypeStr) {
        this.dataTypeStr = dataTypeStr;
    }

    /**
     * 获取质量
     *
     * @return capacity - 质量
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * 设置质量
     *
     * @param capacity 质量
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}