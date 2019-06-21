package com.example;

import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Bean
    public MapConfig mapConfig() {
        MapConfig mapConfig = new MapConfig("default");
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.PER_NODE));
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
        return mapConfig;
    }

}
