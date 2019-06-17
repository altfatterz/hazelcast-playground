package com.example;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setGroupConfig(new GroupConfig("order-service"));
        return config;
    }
}
