package cn.com.bluemoon.admin.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{

	private ApplicationContext applicationContext;

    public WebConfig(){
        super();
    }
    
    @Bean
	public AuthenticationInterceptor authenticationInterceptor(){
		return new AuthenticationInterceptor();
	}
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/template/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/template/");
        super.addResourceHandlers(registry);
        }
    
    @Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：除了login，其他都拦截判断
        registry.addInterceptor(this.authenticationInterceptor()).addPathPatterns("/**").excludePathPatterns("/toLogin","/login","/error","/logout","/index");
        super.addInterceptors(registry);
    }
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "forward:/toLogin" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
        return;
    }
}
