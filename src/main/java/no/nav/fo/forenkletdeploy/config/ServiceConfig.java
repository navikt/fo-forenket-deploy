package no.nav.fo.forenkletdeploy.config;

import no.nav.fo.forenkletdeploy.commits.stash.StashProvider;
import no.nav.fo.forenkletdeploy.service.ApplicationService;
import no.nav.fo.forenkletdeploy.service.JiraIssueService;
import no.nav.fo.forenkletdeploy.service.VeraDeployService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ApplicationService.class,
        StashProvider.class,
        VeraDeployService.class,
        JiraIssueService.class
})
public class ServiceConfig {

}
