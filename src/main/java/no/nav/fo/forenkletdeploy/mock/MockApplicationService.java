package no.nav.fo.forenkletdeploy.mock;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.provider.TeamProvider;
import no.nav.fo.forenkletdeploy.service.ApplicationService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Component
public class MockApplicationService extends ApplicationService {
    @Inject
    public MockApplicationService(TeamProvider teamProvider) {
        super(teamProvider);
    }

    @Override
    protected List<ApplicationConfig> getAllAppConfigurations() {
        return Arrays.asList(
                ApplicationConfig.builder()
                        .name("tulleapp")
                        .gitUrl("ssh://git@github.com/tulleapp")
                        .build(),
                ApplicationConfig.builder()
                        .name("pusapp")
                        .gitUrl("ssh://git@github.com/puseapp")
                        .build(),
                ApplicationConfig.builder()
                        .name("testeapp")
                        .gitUrl("ssh://git@github.com/testeapp")
                        .build()
        );
    }

    @Override
    public List<ApplicationConfig> getAppsByTeam(String teamId) {
        return getAllAppConfigurations();
    }
}
