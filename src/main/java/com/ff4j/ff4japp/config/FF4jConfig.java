package com.ff4j.ff4japp.config;

import java.io.FileNotFoundException;

import javax.sql.DataSource;

import org.ff4j.FF4j;
import org.ff4j.cache.FF4JCacheManager;
import org.ff4j.cache.FF4jCacheManagerRedis;
import org.ff4j.conf.XmlConfig;
import org.ff4j.redis.RedisConnection;
import org.ff4j.security.SpringSecurityAuthorisationManager;
import org.ff4j.springjdbc.store.EventRepositorySpringJdbc;
import org.ff4j.springjdbc.store.FeatureStoreSpringJdbc;
import org.ff4j.springjdbc.store.PropertyStoreSpringJdbc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class FF4jConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUserName;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String jdbcDriver;

    @Value("${redis.hostname}")
    private String redisHostName;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.password}")
    private String redisPassword;

 

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource jdbc = new DriverManagerDataSource();
        jdbc.setDriverClassName(jdbcDriver);
        jdbc.setUrl(jdbcUrl);
        jdbc.setPassword(jdbcPassword);
        jdbc.setUsername(jdbcUserName);
        return jdbc;
    }

    @Bean
    public FF4j getFF4j(DataSource dataSource) throws FileNotFoundException {

        FF4j ff4j = new FF4j();

        /*
         * Implementation of each store. Here this is boiler plate as if nothing
         * is specified the inmemory is used. Those are really the one that will
         * change depending on your technology.
         */
        ff4j.setFeatureStore(new FeatureStoreSpringJdbc(dataSource));
        ff4j.setPropertiesStore(new PropertyStoreSpringJdbc(dataSource));
        ff4j.setEventRepository(new EventRepositorySpringJdbc(dataSource));
        
        // ff4j.setFeatureStore(new InMemoryFeatureStore());
        // ff4j.setPropertiesStore(new InMemoryPropertyStore());
        // ff4j.setEventRepository(new InMemoryEventRepository());
        // Enabling audit and monitoring, default value is false
        // ff4j.audit(true);

        // When evaluting not existing features, ff4j will create then but disabled
        ff4j.autoCreate(true);

        // To define RBAC access, the application must have a logged user
        // ff4j.setAuthManager(...);

        // Look for permissions and all things in Spring Security
        ff4j.setAuthorizationsManager(new SpringSecurityAuthorisationManager());

        // To define a cacher layer to relax the DB, multiple implementations

        RedisConnection redisConnection = new RedisConnection(redisHostName, redisPort);
        FF4JCacheManager ff4jCache = new FF4jCacheManagerRedis(redisConnection);
        ff4j.cache(ff4jCache);

        XmlConfig xmlConfig = ff4j.parseXmlConfig("ff4j.xml");
        ff4j.getFeatureStore().importFeatures(xmlConfig.getFeatures().values());
        ff4j.getPropertiesStore().importProperties(xmlConfig.getProperties().values());

        return ff4j;
    }
}
