package no.nav.fo.forenkletdeploy.config;

import no.nav.fo.forenkletdeploy.commits.github.GithubProvider;
import no.nav.fo.forenkletdeploy.commits.stash.StashCommitProvider;
import no.nav.fo.forenkletdeploy.commits.stash.StashTagProvider;
import no.nav.fo.forenkletdeploy.provider.TeamProvider;
import no.nav.fo.forenkletdeploy.service.TeamService;
import no.nav.fo.forenkletdeploy.service.UnleashService;
import no.nav.fo.forenkletdeploy.service.VersionControlService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        TeamProvider.class,
        GithubProvider.class,
        UnleashService.class,
        StashCommitProvider.class,
        StashTagProvider.class,
        VersionControlService.class,
        TeamService.class
})
@ComponentScan("no.nav.fo.forenkletdeploy.rest")
public class CommonConfig {
}
