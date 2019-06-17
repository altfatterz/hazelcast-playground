package com.example;

import org.springframework.boot.actuate.metrics.cache.CacheMetricsRegistrar;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * https://stackoverflow.com/questions/49697063/after-upgrade-to-spring-boot-2-how-to-expose-cache-metrics-to-prometheus
 */

@Component
public class CacheMetricsRegistration {

    private final CacheMetricsRegistrar cacheMetricsRegistrar;

    private final CacheManager cacheManager;

    public CacheMetricsRegistration(CacheMetricsRegistrar cacheMetricsRegistrar, CacheManager cacheManager) {
        this.cacheMetricsRegistrar = cacheMetricsRegistrar;
        this.cacheManager = cacheManager;
    }

    @EventListener(ApplicationReadyEvent.class) // can this be delayed until the cache is created dynamically?
    public void register() {
        Cache customers = this.cacheManager.getCache("customers");
        this.cacheMetricsRegistrar.bindCacheToRegistry(customers);
    }
}
