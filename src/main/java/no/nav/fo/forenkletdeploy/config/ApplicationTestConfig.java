package no.nav.fo.forenkletdeploy.config;

import no.nav.fo.forenkletdeploy.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

@Configuration
@Import({
        MockConfig.class,
        CommonConfig.class
})
public class ApplicationTestConfig {
    @Bean
    FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new CorsFilter());
        frb.setUrlPatterns(Arrays.asList("/*"));
        return frb;
    }
}
