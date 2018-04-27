package no.nav.fo.forenkletdeploy.mock;

import no.nav.fo.forenkletdeploy.commits.stash.StashCommitProvider;
import no.nav.fo.forenkletdeploy.commits.stash.StashProvider;
import no.nav.fo.forenkletdeploy.commits.stash.StashTagProvider;
import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Commit;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MockCommitProvider extends StashProvider {

    public MockCommitProvider() {
        super(null, null);
    }

    private static Long getMinutesAgo(Integer minutes) {
        return (System.currentTimeMillis()) - (minutes * 60 * 1000);
    }

    @Override
    public List<Commit> getCommitsForRelease(ApplicationConfig application, String fromTag, String toTag) {
        String app = application.getName();
        String repo = application.getGitUrl();

        return Arrays.asList(
                Commit.builder()
                        .hash("1402a360346aefd")
                        .application(app)
                        .url(repo)
                        .message("Merge pull-request #35 into master")
                        .author("Dummy the Cat")
                        .timestamp(getMinutesAgo(1))
                        .mergecommit(true)
                        .build(),

                Commit.builder()
                        .hash("feda134124cd323418")
                        .application(app)
                        .url(repo)
                        .message("PUS-27 Adding UU-definition file")
                        .author("Dummy the Cat")
                        .timestamp(getMinutesAgo(3))
                        .mergecommit(false)
                        .build(),

                Commit.builder()
                        .hash("98cdf452afcde")
                        .application(app)
                        .url(repo)
                        .message("PUS-11 Legger til en feature som er feature-togglet vekk")
                        .author("Dummy the Cat")
                        .timestamp(getMinutesAgo(12))
                        .mergecommit(false)
                        .build(),

                Commit.builder()
                        .hash("a6a7d6d4cs65f")
                        .application(app)
                        .url(repo)
                        .message("PUS-12 En dummybeskrivelse av en issue som jeg løste")
                        .author("Dummy the Cat")
                        .timestamp(getMinutesAgo(72))
                        .mergecommit(false)
                        .build(),

                Commit.builder()
                        .hash("425fdfa1341de2")
                        .application(app)
                        .url(repo)
                        .message("Refakturerer en feature som ikke hører til brukerhistorie")
                        .author("Dummy the Cat")
                        .timestamp(getMinutesAgo(60 * 24))
                        .mergecommit(false)
                        .build(),

                Commit.builder()
                        .hash("4325cfda24de3424d")
                        .application(app)
                        .url(repo)
                        .message("FO-123 Fikset en dummy-feature med lite testbehov")
                        .author("Dummy the Cat")
                        .timestamp(getMinutesAgo(60 * 24 * 3))
                        .mergecommit(false)
                        .build()
        );
    }
}
