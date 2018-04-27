package no.nav.fo.forenkletdeploy.service;

import no.nav.fo.forenkletdeploy.commits.github.GithubProvider;
import no.nav.fo.forenkletdeploy.commits.stash.StashProvider;
import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Commit;
import no.nav.fo.forenkletdeploy.domain.Tag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class VersionControlService {
    private final GithubProvider githubProvider;
    private final StashProvider stashProvider;

    @Inject
    public VersionControlService(GithubProvider githubProvider, StashProvider stashProvider) {
        this.githubProvider = githubProvider;
        this.stashProvider = stashProvider;
    }

    @Cacheable(value = "commits", keyGenerator = "cacheKeygenerator")
    public List<Commit> getCommitsForRelease(ApplicationConfig application, String fromVersion, String toVersion) {
        if (isGithubCommit(application.getGitUrl())) {
            return githubProvider.getCommitsForRelease(application, fromVersion, toVersion);
        }
        return stashProvider.getCommitsForRelease(application, fromVersion, toVersion);
    }

    @Cacheable(value = "tags", keyGenerator = "cacheKeygenerator")
    public List<Tag> getTagsForApplication(ApplicationConfig applicationConfig) {
        if (isGithubCommit(applicationConfig.getGitUrl())) {
            return githubProvider.getTagsForApplication(applicationConfig);
        }
        return stashProvider.getTagsForApplication(applicationConfig);
    }

    private boolean isGithubCommit(String repoUri) {
        return repoUri.contains("github.com");
    }

}
