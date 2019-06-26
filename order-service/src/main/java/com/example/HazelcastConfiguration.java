package com.example;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelcastConfig(EurekaClient eurekaClient) {
        EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);
        Config config = new Config();

        config.getMapConfig("default").getMaxSizeConfig().setSize(400).setMaxSizePolicy(MaxSizeConfig.MaxSizePolicy.PER_NODE);
        config.getMapConfig("default").setEvictionPolicy(EvictionPolicy.LFU);

        config.getManagementCenterConfig().setEnabled(true);
        config.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter/");

        // this works locally, we can control the ips used by hazelcast. Check your `ifconfig` and put and ip which you like to use for Hazelcast
        config.getNetworkConfig().getInterfaces().setEnabled(true)
                .setInterfaces(Arrays.asList("192.*.*.*"));

        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getEurekaConfig()
                .setEnabled(true)
                .setProperty("self-registration", "true")
                .setProperty("namespace", "hazelcast")
                .setProperty("use-metadata-for-host-and-port", "true");

        return config;
    }
}
