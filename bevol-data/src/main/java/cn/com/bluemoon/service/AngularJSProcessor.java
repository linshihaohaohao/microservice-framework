package cn.com.bluemoon.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com
 * @since 0.5.0
 */
public class AngularJSProcessor implements PageProcessor {

    private Site site = Site.me();

    private static final String ARITICALE_URL = "http://angularjs\\.cn/api/article/\\w+";

    //    private static final String LIST_URL = "http://angularjs\\.cn/api/article/latest.*";
    private static final String LIST_URL = "https://api.bevol.cn/search/goods/index3\\?p=\\d+&keywords=\\d+";



    @Override
    public void process(Page page) {
        page.putField("mid====", new JsonPathSelector("$.data.items[*].mid").selectList(page.getRawText()));
        page.putField("title======", new JsonPathSelector("$.data.items[*].title").selectList(page.getRawText()));

        if (page.getUrl().regex(LIST_URL).match()) {
            List<Integer> categoryList = new ArrayList<>();
            categoryList.add(6);
            categoryList.add(7);
            categoryList.add(8);
            categoryList.add(9);
            categoryList.add(10);
            categoryList.add(11);
            categoryList.add(12);
            categoryList.add(13);
            categoryList.add(15);
            categoryList.add(20);
            categoryList.add(47);
            categoryList.add(30);
            categoryList.add(38);
            categoryList.forEach(integer -> {
                page.addTargetRequest("https://api.bevol.cn/search/goods/index3?p=1&keywords=" + integer);
            });
            String str = new JsonPathSelector("$.data.total").select(page.getRawText());
            String url = page.getUrl().get();
           String[] s = url.split("keywords=");
           String keyword = s[1];
            if (str != null) {
                for (int i = 1; i < Integer.parseInt(str) / 20 + 2; i++) {
                    page.addTargetRequest("https://api.bevol.cn/search/goods/index3?p=" + i + "&keywords="+keyword);
                }
            }
//            String url = null;
//            List<String> ids = new JsonPathSelector("$.data.items.mid").selectList(page.getRawText());
//            for (int i = 0; i < ids.size(); i++) {
//                Map<String, Object> nameValuePair = new HashMap<String, Object>();
//                NameValuePair[] values = new NameValuePair[3];
//                values[0] = new BasicNameValuePair("model", "iphone");
//                values[1] = new BasicNameValuePair("o", "IOS");
//                values[2] = new BasicNameValuePair("sys", "11.4");
//                values[3] = new BasicNameValuePair("uuid", "FF814384-4B8A-4153-863D-D7F8AB115D06");
//                values[4] = new BasicNameValuePair("v", "3.3.1");
//                values[5] = new BasicNameValuePair("mid", ids.get(i));
//                nameValuePair.put("nameValuePair", values);
//                url = "http://api.bevol.cn/entity/info2/goods";
//                Request request = new Request(url);
//                request.setExtras(nameValuePair);
//                request.setMethod(HttpConstant.Method.POST);
//                page.addTargetRequest(request);
//            }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

            Spider.create(new AngularJSProcessor()).addUrl("https://api.bevol.cn/search/goods/index3?p=1&keywords=6").thread(10).run();
//        Spider.create(new AngularJSProcessor()).addUrl("http://angularjs.cn/api/article/latest?p=1&s=20").run();
    }
}
