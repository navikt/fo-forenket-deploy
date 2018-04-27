package no.nav.fo.forenkletdeploy.commits.stash;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Commit;
import no.nav.fo.forenkletdeploy.domain.Tag;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class StashProvider {

    public static String API_BASE_URL = "http://stash.devillo.no/rest/api/1.0";

    private final StashCommitProvider commitProvider;
    private final StashTagProvider tagProvider;

    @Inject
    public StashProvider(StashCommitProvider stashCommitProvider, StashTagProvider stashTagProvider) {
        this.commitProvider = stashCommitProvider;
        this.tagProvider = stashTagProvider;
    }

    public List<Commit> getCommitsForRelease(ApplicationConfig application, String fromVersion, String toVersion) {
        return commitProvider.getCommitsForRelease(application, fromVersion, toVersion);
    }

    public List<Tag> getTagsForApplication(ApplicationConfig applicationConfig) {
        return tagProvider.getTagsForApplication(applicationConfig);
    }

}
