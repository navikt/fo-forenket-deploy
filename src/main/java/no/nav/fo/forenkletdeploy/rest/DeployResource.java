package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.VeraDeploy;
import no.nav.fo.forenkletdeploy.service.ApplicationService;
import no.nav.fo.forenkletdeploy.service.VeraDeployService;
import no.nav.fo.forenkletdeploy.util.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/deploy")
public class DeployResource {
    private final VeraDeployService veraDeployService;
    private final ApplicationService applicationService;
    private final List<String> gyldigeMiljoer = Arrays.asList("p", "q0", "q6", "t6");

    @Inject
    public DeployResource(VeraDeployService veraDeployService, ApplicationService applicationService) {
        this.veraDeployService = veraDeployService;
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<VeraDeploy> getAllDeploys(
            @RequestParam("team") String teamId
    ) {
        return getVeraDeploys(applicationService.getAppsByTeam(teamId));
    }

    @GetMapping("/{application}")
    public List<VeraDeploy> getAllDeploysForApplication(@PathVariable("application") String application) {
        return getVeraDeploys(applicationService.getApps()).stream()
                .filter(veraDeploy -> veraDeploy.application.equalsIgnoreCase(application))
                .collect(Collectors.toList());
    }

    @GetMapping("/{application}/{environment}")
    public VeraDeploy getDeploy(
            @PathVariable("application") String application,
            @PathVariable("environment") String environment
    ) {
        return getVeraDeploys(applicationService.getApps()).stream()
                .filter(deploy -> deploy.application.equalsIgnoreCase(application))
                .filter(deploy -> deploy.environment.equalsIgnoreCase(environment))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Fant ikke VeraDeploy for %s i %s", application, environment)));
    }

    private List<VeraDeploy> getVeraDeploys(List<ApplicationConfig> applicationConfigs) {
        List<String> applicationNames = applicationConfigs.stream()
                .map(ApplicationConfig::getName)
                .collect(Collectors.toList());
        return veraDeployService.getVeraDeploys().stream()
                .filter(deploy -> gyldigeMiljoer.contains(deploy.environment))
                .filter(deploy -> applicationNames.contains(deploy.application))
                .collect(Collectors.toList());
    }

}
