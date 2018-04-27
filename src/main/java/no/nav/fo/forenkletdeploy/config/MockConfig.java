package no.nav.fo.forenkletdeploy.config;

import no.nav.fo.forenkletdeploy.mock.MockApplicationService;
import no.nav.fo.forenkletdeploy.mock.MockCommitProvider;
import no.nav.fo.forenkletdeploy.mock.MockJiraIssueService;
import no.nav.fo.forenkletdeploy.mock.MockVeraDeployService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        MockApplicationService.class,
        MockCommitProvider.class,
        MockVeraDeployService.class,
        MockJiraIssueService.class
})
public class MockConfig {
}
