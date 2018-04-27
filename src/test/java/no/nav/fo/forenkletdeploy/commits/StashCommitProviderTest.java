package no.nav.fo.forenkletdeploy.commits;

import no.nav.fo.forenkletdeploy.commits.stash.StashCommitProvider;
import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import org.junit.Test;

import static org.junit.Assert.*;

public class StashCommitProviderTest {

    @Test
    public void skalLageGyldigeUrlerForFOApp() {
        ApplicationConfig applicationConfig = ApplicationConfig.builder()
                .gitUrl("ssh://git@stash.devillo.no:7999/fo/aktivitetsplan.git")
                .name("aktivitetsplan")
                .build();

        String expected = "http://stash.devillo.no/rest/api/1.0/projects/fo/repos/aktivitetsplan/commits";
        assertEquals(expected, StashCommitProvider.getRestUriForRepo(applicationConfig));
    }

    @Test
    public void skalLageGyldigeUrlerForPUSApp() {
        ApplicationConfig applicationConfig = ApplicationConfig.builder()
                .gitUrl("ssh://git@stash.devillo.no:7999/fa/docker-builder.git")
                .name("docker-builder")
                .build();

        String expected = "http://stash.devillo.no/rest/api/1.0/projects/fa/repos/docker-builder/commits";
        assertEquals(expected, StashCommitProvider.getRestUriForRepo(applicationConfig));
    }
}