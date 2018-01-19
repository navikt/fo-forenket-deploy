package no.nav.fo.forenkletdeploy.service;

import no.nav.fo.forenkletdeploy.TestUtils;
import no.nav.fo.forenkletdeploy.provider.TeamProvider;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationServiceTest {
    private ApplicationService applicationService = new ApplicationService(new TeamProvider());

    @Before
    public void setup() {
        TestUtils.setupContext();
    }

    @Test
    public void getApps() {
        assertThat(applicationService.getApps()).isNotEmpty();
    }
}