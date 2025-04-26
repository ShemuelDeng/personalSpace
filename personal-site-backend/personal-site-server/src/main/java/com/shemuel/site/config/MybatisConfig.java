package com.shemuel.site.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.shemuel.site.tools.mp.UserIdAutoFillInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MybatisConfig {

    @Bean
    public UserIdAutoFillInterceptor userIdAutoFillInterceptor() {
        return new UserIdAutoFillInterceptor();
    }

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.addInterceptor(userIdAutoFillInterceptor());
    }
}