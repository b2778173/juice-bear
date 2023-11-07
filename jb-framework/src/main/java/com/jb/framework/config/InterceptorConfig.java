package com.jb.framework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "exclude")
public class InterceptorConfig {

    // 签名
    private List<String> sign;

    // token
    private List<String> token;

    // captcha
    private List<String> oldCaptchaUri;
    private List<String> newCaptchaUri;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("interceptor/exclude.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}