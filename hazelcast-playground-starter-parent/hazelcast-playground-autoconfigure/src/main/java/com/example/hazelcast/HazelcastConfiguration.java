package com.example.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Hazelcast.class)
@ConditionalOnMissingBean(HazelcastInstance.class)
@ConditionalOnProperty(value = "spring.cache.type", havingValue = "hazelcast", matchIfMissing = true)
public class HazelcastConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HazelcastConfiguration.class);

    @Bean
    @ConditionalOnClass(value = {EurekaClient.class, MapConfig.class})
    public Config hazelcastConfig(EurekaClient eurekaClient, MapConfig mapConfig) {
        log.info("Using MapConfig with: {}", mapConfig);

        EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);
        Config config = new Config();

        config.getManagementCenterConfig().setEnabled(true);
        config.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter/");

        config.addMapConfig(mapConfig);

        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getEurekaConfig()
                .setEnabled(true)
                .setProperty("self-registration", "true")
                .setProperty("namespace", "hazelcast")
                .setProperty("use-metadata-for-host-and-port", "true");
        return config;
    }
}