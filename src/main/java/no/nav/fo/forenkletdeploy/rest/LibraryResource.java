package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.service.ApplicationService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/libraries")
@Component
public class LibraryResource {

    private final ApplicationService applicationService;

    @Inject
    public LibraryResource(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GET
    public List<ApplicationConfig> getLibraries(@QueryParam("team") String teamId) {
        return applicationService.getLibrariesByTeam(teamId);
    }

}
