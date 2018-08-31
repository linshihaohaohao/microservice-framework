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
package cn.com.bluemoon.utils;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * @ClassName: HttpClientTool
 * @Description:
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: shihao-lin@msyc.cc
 * @Date: 2018/8/14 17:25
 */
public class HttpClientTool {
    // org.apache.http.impl.client.CloseableHttpClient
    private static CloseableHttpClient httpclient = null;

    // 这里就直接默认固定了,因为以下三个参数在新建的method中仍然可以重新配置并被覆盖.
    static final int connectionRequestTimeout = 5000;// ms毫秒,从池中获取链接超时时间
    static final int connectTimeout = 5000;// ms毫秒,建立链接超时时间
    static final int socketTimeout = 30000;// ms毫秒,读取超时时间
    public static volatile boolean isClosed = false;
    private static PoolingHttpClientConnectionManager poolConnManager;

    // 总配置,主要涉及是以下两个参数,如果要作调整没有用到properties会比较后麻烦,但鉴于一经粘贴,随处可用的特点,就不再做依赖性配置化处理了.
    // 而且这个参数同一家公司基本不会变动.
    static final int maxTotal = 500;// 最大总并发,很重要的参数
    static final int maxPerRoute = 100;// 每路并发,很重要的参数

    // 正常情况这里应该配成MAP或LIST
    // 细化配置参数,用来对每路参数做精细化处理,可以管控各ip的流量,比如默认配置请求baidu:80端口最大100个并发链接,
//    static final String detailHostName = "http://www.baidu.com";// 每个细化配置之ip(不重要,在特殊场景很有用)
//    static final int detailPort = 80;// 每个细化配置之port(不重要,在特殊场景很有用)
//    static final int detailMaxPerRoute = 100;// 每个细化配置之最大并发数(不重要,在特殊场景很有用)

    public static CloseableHttpClient getHttpClient() {
        if (null == httpclient) {
            synchronized (HttpClientTool.class) {
                if (null == httpclient) {
                    httpclient = init();
                }
            }
        }
        return httpclient;
    }

    /**
     * 链接池初始化 这里最重要的一点理解就是. 让CloseableHttpClient 一直活在池的世界里, 但是HttpPost却一直用完就消掉.
     * 这样可以让链接一直保持着.
     *
     * @return
     */
    private static CloseableHttpClient init() {
        CloseableHttpClient newHttpclient = null;

        // 设置连接池
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainsf).register("https", sslsf).build();
        poolConnManager = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        poolConnManager.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        poolConnManager.setDefaultMaxPerRoute(maxPerRoute);

        // 细化配置开始,其实这里用Map或List的for循环来配置每个链接,在特殊场景很有用.
        // 将每个路由基础的连接做特殊化配置,一般用不着
//        HttpHost httpHost = new HttpHost(detailHostName, detailPort);
//        // 将目标主机的最大连接数增加
//        cm.setMaxPerRoute(new HttpRoute(httpHost), detailMaxPerRoute);
        // cm.setMaxPerRoute(new HttpRoute(httpHost2),
        // detailMaxPerRoute2);//可以有细化配置2
        // cm.setMaxPerRoute(new HttpRoute(httpHost3),
        // detailMaxPerRoute3);//可以有细化配置3
        // 细化配置结束

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 2) {// 如果已经重试了2次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        newHttpclient = HttpClients.custom().setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).setRetryHandler(httpRequestRetryHandler).build();
        return newHttpclient;
    }

    /**
     * 关闭连接池资源
     */
    public static void closePool() {
        if( !isClosed ){
            isClosed = true;
            poolConnManager.close();
        }
    }
}
