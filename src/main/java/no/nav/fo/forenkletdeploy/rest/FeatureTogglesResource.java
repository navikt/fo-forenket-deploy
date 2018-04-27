package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.FeatureToggle;
import no.nav.fo.forenkletdeploy.service.UnleashService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/featuretoggles")
public class FeatureTogglesResource {
    private UnleashService unleashService;

    @Inject
    public FeatureTogglesResource(UnleashService unleashService) {
        this.unleashService = unleashService;
    }

    @GetMapping("/{togglename}")
    public FeatureToggle getToggleByName(@PathVariable("togglename") String togglename) {
        return FeatureToggle.builder()
                .name(togglename)
                .enabled(unleashService.isEnabled(togglename))
                .build();
    }
}
