/**
 * 广州市两棵树网络科技有限公司版权所有
 * DT Group Technology & commerce Co., LtdAll rights reserved.
 * <p>
 * 广州市两棵树网络科技有限公司，创立于2009年。旗下运营品牌洋葱小姐。
 * 洋葱小姐（Ms.Onion） 下属三大业务模块 [洋葱海外仓] , [洋葱DSP] , [洋葱海外聚合供应链]
 * [洋葱海外仓]（DFS）系中国海关批准的跨境电商自营平台(Cross-border ecommerce platform)，
 * 合法持有海外直邮保税模式的跨境电商营运资格。是渠道拓展，平台营运，渠道营运管理，及客户服务等前端业务模块。
 * [洋葱DSP]（DSP）系拥有1.3亿消费者大数据分析模型。 是基于客户的消费行为，消费轨迹，及多维度云算法(MDPP)
 * 沉淀而成的精准消费者模型。洋葱DSP能同时为超过36种各行业店铺 及200万个销售端口
 * 进行多店铺高精度配货，并能预判消费者购物需求进行精准推送。同时为洋葱供应链提供更前瞻的商品采买需求模型 。
 * [洋葱海外聚合供应链]（Super Supply Chain）由中国最大的进口贸易集团共同
 * 合资成立，拥有20余年的海外供应链营运经验。并已入股多家海外贸易企业，与欧美澳等9家顶级全球供应商达成战略合作伙伴关系。
 * 目前拥有835个国际品牌直接采买权，12万个单品的商品供应库。并已建设6大海外直邮仓库，为国内客户提供海外商品采买集货供应，
 * 跨境 物流，保税清关三合一的一体化模型。目前是中国唯一多模式聚合的海外商品供应链 。
 * <p>
 * 洋葱商城：http://m.msyc.cc/wx/indexView?tmn=1
 * <p>
 * 洋桃商城：http://www.yunyangtao.com
 */
package cn.com.bluemoon.domain.vo;

import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ItemCompositionVo
 * @Description:
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: shihao-lin@msyc.cc
 * @Date: 2018/8/14 10:25
 */
public class ItemCompositionVo {

    private Long idx;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 0：删除（软删除）， 1：可用 , 2: 未激活
     */
    private Short status;

    private Date createTime;

    private Date updateTime;

    private String mid;

    private Integer bevolId;

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
    private String acneRisk;

    private String cas;

    private String otherTitle;

    private String remark;

    private String shenYong;

    private String usedNum;

    private String used;

    /**
     * 使用目的 json组成
     */
    private List<T> useds;

    private String curUsedName;

    /**
     * 安全风险
     */
    private String safety;

    /**
     * 使用目的 便于显示
     */
    private String usedTitle;

    private String cmTitle;

    private String cmEnglish;

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getBevolId() {
        return bevolId;
    }

    public void setBevolId(Integer bevolId) {
        this.bevolId = bevolId;
    }

    public Integer getBevolSrcId() {
        return bevolSrcId;
    }

    public void setBevolSrcId(Integer bevolSrcId) {
        this.bevolSrcId = bevolSrcId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAcneRisk() {
        return acneRisk;
    }

    public void setAcneRisk(String acneRisk) {
        this.acneRisk = acneRisk;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getOtherTitle() {
        return otherTitle;
    }

    public void setOtherTitle(String otherTitle) {
        this.otherTitle = otherTitle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShenYong() {
        return shenYong;
    }

    public void setShenYong(String shenYong) {
        this.shenYong = shenYong;
    }

    public String getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(String usedNum) {
        this.usedNum = usedNum;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public List<T> getUseds() {
        return useds;
    }

    public void setUseds(List<T> useds) {
        this.useds = useds;
    }

    public String getCurUsedName() {
        return curUsedName;
    }

    public void setCurUsedName(String curUsedName) {
        this.curUsedName = curUsedName;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getUsedTitle() {
        return usedTitle;
    }

    public void setUsedTitle(String usedTitle) {
        this.usedTitle = usedTitle;
    }

    public String getCmTitle() {
        return cmTitle;
    }

    public void setCmTitle(String cmTitle) {
        this.cmTitle = cmTitle;
    }

    public String getCmEnglish() {
        return cmEnglish;
    }

    public void setCmEnglish(String cmEnglish) {
        this.cmEnglish = cmEnglish;
    }
}
