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
package cn.com.bluemoon.service;

import cn.com.bluemoon.mapper.TbBevolItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BevolDataProcessor
 * @Description:
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: shihao-lin@msyc.cc
 * @Date: 2018/8/23 18:10
 */
public class BevolDataProcessor implements PageProcessor {


    private static Site site = Site.me()
            .setCharset("UTF-8")
            .addHeader("Content-Type", "application/x-www-form-urlencoded");

    private Request request = new Request("http://api.bevol.cn/entity/info2/goods");

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {

        String str = new JsonPathSelector("$.result").select(page.getRawText());
        page.putField("mid====", new JsonPathSelector("$.result.entityInfo.composition[*].id").selectList(page.getRawText()));
        page.putField("title======", new JsonPathSelector("$.result.entityInfo.composition[*]..title").selectList(page.getRawText()));

//        List<TbBevolItem> tbBevolItems = tbBevolItemMapper.selectAll();
//        tbBevolItems.forEach(tbBevolItem -> {
//            String url = "http://api.bevol.cn/entity/info2/goods";
//            Request request = new Request(url);
//            String json ="mid=359db1d5fe35be559b13a0d9368d4180&model=iPhone&o=iOS&sys_v=11.4&uuid=FF814384-4B8A-4153-863D-D7F8AB115D06&v=3.3.1";
//            request.setRequestBody(HttpRequestBody.json(json,"utf-8"));
//            request.setMethod(HttpConstant.Method.POST);
//            page.addTargetRequest(request);
//        });

    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return site;
    }

    private Request getRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("model", "iphone");
        map.put("o", "IOS");
        map.put("sys", "11.4");
        map.put("uuid", "FF814384-4B8A-4153-863D-D7F8AB115D06");
        map.put("v", "3.3.1");
        map.put("mid", "359db1d5fe35be559b13a0d9368d4180");
        this.request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "utf-8"));
        return this.request;
    }

    public static void main(String[] args) {
        BevolDataProcessor hanjuTvProcessor = new BevolDataProcessor();
        System.out.println(hanjuTvProcessor.getRequest());
        System.out.println(hanjuTvProcessor.getSite());
        Spider.create(hanjuTvProcessor).addRequest(hanjuTvProcessor.getRequest()).thread(5).run();
    }
}
