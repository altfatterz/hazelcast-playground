package com.example.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Hazelcast.class)
@ConditionalOnMissingBean(HazelcastInstance.class)
public class HazelcastConfiguration {

    @Bean
    @ConditionalOnClass(EurekaClient.class)
    public Config hazelcastConfig(EurekaClient eurekaClient) {
        EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getEurekaConfig()
                .setEnabled(true)
                .setProperty("self-registration", "true")
                .setProperty("namespace", "hazelcast")
                .setProperty("use-metadata-for-host-and-port", "true");
        return config;
    }
}