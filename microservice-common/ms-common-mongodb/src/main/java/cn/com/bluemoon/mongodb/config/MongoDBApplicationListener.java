package cn.com.bluemoon.mongodb.config;

import cn.com.bluemoon.mongodb.contentthread.SpringContextUtil;

import com.mongodb.ReadPreference;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by leonwong on 2016/10/21.
 */
public class MongoDBApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) applicationEvent).getApplicationContext();
            MongoTemplate mongoTemplate = null;
            try {
                mongoTemplate = (MongoTemplate) applicationContext.getBean("mongoTemplate");
                mongoTemplate.setReadPreference(ReadPreference.secondaryPreferred());
            } catch (BeansException e) {

            }
            new SpringContextUtil().setApplicationContext(applicationContext);
        }
    }
}
