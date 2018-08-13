package cn.com.bluemoon.admin.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class BmWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
/*        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);*/
        registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
        .allowCredentials(false).maxAge(3600);
    }
}
