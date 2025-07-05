package com.mycompany.sidebysidesample.gen.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Country.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Department.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Department.class.getName() + ".employees");
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Employee.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Employee.class.getName() + ".jobs");
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Job.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Job.class.getName() + ".tasks");
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.JobHistory.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Location.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Region.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Task.class.getName());
            createCache(cm, com.mycompany.sidebysidesample.gen.domain.Task.class.getName() + ".jobs");
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
