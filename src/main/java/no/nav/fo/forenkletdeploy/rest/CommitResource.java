package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Commit;
import no.nav.fo.forenkletdeploy.service.ApplicationService;
import no.nav.fo.forenkletdeploy.service.VersionControlService;
import no.nav.fo.forenkletdeploy.util.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RequestMapping("/api/commit")
@RestController
public class CommitResource {
    private final ApplicationService applicationService;
    private final VersionControlService versionControlService;

    @Inject
    public CommitResource(ApplicationService applicationService, VersionControlService versionControlService) {
        this.applicationService = applicationService;
        this.versionControlService = versionControlService;
    }

    @GetMapping("/{application}")
    public List<Commit> getCommitsForApplication(
            @PathVariable("application") String application,
            @RequestParam("fromVersion") String fromVersion,
            @RequestParam("toVersion") String toVersion
    ) {
        ApplicationConfig applicationConfig = applicationService.getAppByName(application);
        if (applicationConfig == null) {
            throw new NotFoundException("Kunne ikke finne ApplicationConfig for  " + application);
        }
        return versionControlService.getCommitsForRelease(applicationConfig, fromVersion, toVersion);
    }
}
