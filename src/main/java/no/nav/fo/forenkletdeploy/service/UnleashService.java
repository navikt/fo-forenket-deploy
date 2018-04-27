package no.nav.fo.forenkletdeploy.service;

import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.util.UnleashConfig;
import no.nav.fo.forenkletdeploy.unleash.streategies.ByEnvironmentStrategy;
import no.nav.fo.forenkletdeploy.unleash.streategies.IsDevStrategy;
import no.nav.fo.forenkletdeploy.unleash.streategies.IsNotProdStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static no.nav.fo.forenkletdeploy.util.Utils.getRequiredProperty;

@Component
public class UnleashService {
    private DefaultUnleash unleash;
    private Logger LOG = LoggerFactory.getLogger(UnleashService.class.getName());

    public UnleashService() {
        String instanceId = getEnvironment();
        String apiUrl = getRequiredProperty("UNLEASH_API_URL");
        LOG.info(String.format("Setting up unleash with instance: '%s' with uri: '%s'", instanceId, apiUrl));

        UnleashConfig config = UnleashConfig.builder()
                .appName("forenklet-deploy")
                .instanceId(instanceId)
                .unleashAPI(apiUrl)
                .build();

        this.unleash = new DefaultUnleash(
                config,
                new ByEnvironmentStrategy(),
                new IsNotProdStrategy(),
                new IsDevStrategy()
        );
    }

    public boolean isEnabled(String toggleName) {
        return this.unleash.isEnabled(toggleName);
    }

    public static String getEnvironment() {
        return Optional.ofNullable(System.getenv("FASIT_ENVIRONMENT_NAME"))
                .orElse("local");

    }
}
