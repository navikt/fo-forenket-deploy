package no.nav.fo.forenkletdeploy.config;

import no.nav.fo.forenkletdeploy.util.CacheKeygenerator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() throws URISyntaxException {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager(
                getClass().getResource("/cache.xml").toURI(),
                cachingProvider.getDefaultClassLoader());
        return new JCacheCacheManager(cacheManager);
    }

    @Bean
    public CacheKeygenerator cacheKeygenerator() {
        return new CacheKeygenerator();
    }
}
