package cn.com.bluemoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 启动器
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class ServiceUserApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(ServiceUserApplication.class, args);  
    }
    
}
