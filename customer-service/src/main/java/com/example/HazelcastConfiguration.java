package com.example;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setManagementCenterConfig(
                new ManagementCenterConfig("https://localhost:8080/hazelcast-mancenter", 3));
        config.setGroupConfig(new GroupConfig("customer-service"));
        return config;
    }
}
