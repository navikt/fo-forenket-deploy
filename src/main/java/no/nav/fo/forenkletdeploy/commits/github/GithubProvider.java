package no.nav.fo.forenkletdeploy.commits.github;

import no.nav.fo.forenkletdeploy.commits.stash.StashProvider;
import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Commit;
import no.nav.fo.forenkletdeploy.domain.Tag;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class GithubProvider {

    private final StashProvider stashProvider;

    @Inject
    public GithubProvider(StashProvider stashProvider) {
        this.stashProvider = stashProvider;
    }

    public List<Commit> getCommitsForRelease(ApplicationConfig application, String fromTag, String toTag) {
        ApplicationConfig applicationStash = getStashConfig(application);

        return stashProvider.getCommitsForRelease(applicationStash, fromTag, toTag);
    }

    public List<Tag> getTagsForApplication(ApplicationConfig applicationConfig) {
        ApplicationConfig applicationStash = getStashConfig(applicationConfig);

        return stashProvider.getTagsForApplication(applicationStash);
    }

    private ApplicationConfig getStashConfig(ApplicationConfig application) {
        return ApplicationConfig.builder()
                .gitUrl(String.format("ssh://git@stash.devillo.no:7999/fa/%s.git", application.getName()))
                .name(application.name)
                .build();
    }

}
