package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Tag;
import no.nav.fo.forenkletdeploy.service.ApplicationService;
import no.nav.fo.forenkletdeploy.service.VersionControlService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/tags")
@Component
public class TagsResource {
    private final ApplicationService applicationService;
    private final VersionControlService versionControlService;

    @Inject
    public TagsResource(ApplicationService applicationService, VersionControlService versionControlService) {
        this.applicationService = applicationService;
        this.versionControlService = versionControlService;
    }

    @GET
    @Path("/{application}")
    public List<Tag> getCommitsForApplication(@PathParam("application") String application) {
        ApplicationConfig applicationConfig = applicationService.getAppByName(application);
        if (applicationConfig == null) {
            throw new NotFoundException("Kunne ikke finne ApplicationConfig for  " + application);
        }
        return versionControlService.getTagsForApplication(applicationConfig);
    }

}
