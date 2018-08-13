package cn.com.bluemoon;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy
@SpringCloudApplication
public class DemoAdminApp extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoAdminApp.class);
    }

    public static void main( String[] args )
    {
    	SpringApplication.run(DemoAdminApp.class, args);
    }
}
