package com.example;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Tag;
import org.springframework.boot.actuate.metrics.cache.CacheMetricsRegistrar;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
        Cache orders = this.cacheManager.getCache("orders");

        Tag tag = new ImmutableTag("cacheManager", "cacheManager");

        this.cacheMetricsRegistrar.bindCacheToRegistry(customers, tag);
        this.cacheMetricsRegistrar.bindCacheToRegistry(orders, tag);
    }
}