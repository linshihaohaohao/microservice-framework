package cn.com.bluemoon.xxljob.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xxl.job.core.executor.XxlJobExecutor;

@Configuration
@EnableConfigurationProperties({XxlJobProperties.class, XxlJobExecutorProperties.class})
public class XxlJobExecutorAutoConfiguration {

	@Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor(XxlJobProperties jobProperties, XxlJobExecutorProperties executorProperties) {
        XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        
        xxlJobExecutor.setAdminAddresses(jobProperties.getRegistUrl());
        xxlJobExecutor.setAccessToken(jobProperties.getAccessToken());
        
        xxlJobExecutor.setAppName(executorProperties.getAppname());
        xxlJobExecutor.setTitle(executorProperties.getTitle());
        xxlJobExecutor.setIp(executorProperties.getIp());
        xxlJobExecutor.setPort(executorProperties.getPort()); 
        xxlJobExecutor.setLogPath(executorProperties.getLogpath());
        
        return xxlJobExecutor;
    }
}
