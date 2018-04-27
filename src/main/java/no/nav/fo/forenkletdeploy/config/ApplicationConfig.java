package no.nav.fo.forenkletdeploy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import({
        CacheConfig.class,
        CommonConfig.class,
        ServiceConfig.class
})
@EnableScheduling
public class ApplicationConfig {
}
