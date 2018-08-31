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

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProcessorData
 * @Description:
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: shihao-lin@msyc.cc
 * @Date: 2018/8/15 11:15
 */
public class ProcessorData implements PageProcessor {
//    public static final String URL_LIST = "http://www.cosdna.com/chs/product.php";
    public static final String URL_LIST = "http://www.cosdna.com/chs/cosmetic_\\w+\\.html";

    public static final String URL_POST = "http://www.cosdna.com/chs/product.php\\?q=.*&p=\\d+";
    List<String> urlList = new ArrayList<>();
    private Site site = Site
            .me()
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        urlList.add(page.getUrl().toString());
//        列表页
        if (page.getUrl().regex(URL_POST).match()) {
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            page.addTargetRequests(page.getHtml().links().regex(URL_POST).all());
            //文章页
        } else {
//            page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
//            page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
//            page.putField("date",
//                    page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));

//            List<String> url = page.getHtml().xpath("//div[@class='ProdResult']//table//tbody//a/@href").all();
//            url.forEach(s -> {
//                System.out.println("http://www.cosdna.com/chs/" + s);
//            });
//            page.putField("content", page.getHtml().xpath("//div[@class='ProdResult']//table//tbody//td[1]//a").all());
            List<String> compotionList = page.getHtml().xpath("//table/tbody/tr/td[1]/a/text()").all();
            System.out.println("成分" + compotionList);
            List<String> nameList = page.getHtml().xpath("//table/tbody/tr/td[2]/a/text()").all();
            System.out.println("名称" + nameList);
            List<String> charactList = page.getHtml().xpath("//table/tbody/tr/td[3]").all();
            System.out.println("特性" + charactList);
            List<String> fenciList = page.getHtml().xpath("//table/tbody/tr/td[5]").all();
            System.out.println("粉刺" + fenciList);
            List<String> cijiList = page.getHtml().xpath("//table/tbody/tr/td[6]").all();
            System.out.println("刺激" + cijiList);
            List<String> anxinduList = page.getHtml().xpath("//table/tbody/tr/td[7]").all();
            System.out.println("安心度" + anxinduList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ProcessorData()).addUrl("http://www.cosdna.com/chs/product.php?q=" + "眼影" + "&p=1")
                .run();
    }
}
