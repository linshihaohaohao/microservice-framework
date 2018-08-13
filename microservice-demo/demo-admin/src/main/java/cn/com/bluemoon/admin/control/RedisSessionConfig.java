package cn.com.bluemoon.admin.control;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(redisNamespace = "demoAdmin") 
public class RedisSessionConfig {
	
}
