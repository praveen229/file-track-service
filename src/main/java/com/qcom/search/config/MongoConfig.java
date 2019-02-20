package com.qcom.search.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class MongoConfig {
	
	@Value("${spring.data.mongodb.host}")
	public String host;
	
	@Value("${spring.data.mongodb.port}")
	public int port;
	
	@Value("${spring.data.mongodb.database}")
	public String dbName;
	
	@Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException{
        return new SimpleMongoDbFactory(new MongoClient(host, port), dbName);
    }
  
    @Bean
    public MongoOperations mongoOperations() throws UnknownHostException{
        return new MongoTemplate(mongoDbFactory());
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer swaggerProperties() {
          PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
          //p.setIgnoreUnresolvablePlaceholders(true);
          return p;
    }

}
