package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.VeraDeploy;
import no.nav.fo.forenkletdeploy.service.ApplicationService;
import no.nav.fo.forenkletdeploy.service.VeraDeployService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/deploy")
@Component
public class DeployResource {
    private final VeraDeployService veraDeployService;
    private final ApplicationService applicationService;
    private final List<String> gyldigeMiljoer = Arrays.asList("p", "q0", "q6", "t6");

    @Inject
    public DeployResource(VeraDeployService veraDeployService, ApplicationService applicationService) {
        this.veraDeployService = veraDeployService;
        this.applicationService = applicationService;
    }

    @GET
    public List<VeraDeploy> getAllDeploys(
            @QueryParam("team") String teamId
    ) {
        return getVeraDeploys(applicationService.getAppsByTeam(teamId));
    }

    @GET
    @Path("/{application}")
    public List<VeraDeploy> getAllDeploysForApplication(@PathParam("application") String application) {
        return getVeraDeploys(applicationService.getApps()).stream()
                .filter(veraDeploy -> veraDeploy.application.equalsIgnoreCase(application))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{application}/{environment}")
    public VeraDeploy getDeploy(
            @PathParam("application") String application,
            @PathParam("environment") String environment
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
