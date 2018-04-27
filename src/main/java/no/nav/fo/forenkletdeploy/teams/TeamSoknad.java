package no.nav.fo.forenkletdeploy.teams;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static no.nav.fo.forenkletdeploy.util.Utils.fromJson;
import static no.nav.fo.forenkletdeploy.util.Utils.getRequiredProperty;
import static no.nav.fo.forenkletdeploy.util.Utils.withClient;


public class TeamSoknad implements Team {

    private List<ApplicationConfig> applicationConfigs = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(TeamSoknad.class.getName());

    @Override
    public String getId() {
        return "sd";
    }

    @Override
    public String getDisplayName() {
        return "Team Søknad";
    }

    @Override
    public String getJenkinsFolder() {
        return "team_soknad";
    }

    @Override
    public List<ApplicationConfig> getApplicationConfigs() {
        return  this.applicationConfigs;
    }

    @Override
    public void hentApplicationConfigs() {
        try {
            String configUrl = "https://raw.githubusercontent.com/navikt/jenkins-dsl-scripts/master/team_soknad/config.json";
            String apiToken = getRequiredProperty("GITHUB_JENKINSPUS_TOKEN");

            String json = withClient(configUrl)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "token " + apiToken)
                    .get(String.class);

            Map<String, Map<String, String>> map = fromJson(json, Map.class);
            this.applicationConfigs = map.entrySet().stream()
                    .map(e -> ApplicationConfig.builder()
                            .name(e.getKey())
                            .gitUrl(e.getValue().get("gitUrl"))
                            .build()
                    )
                    .collect(toList());
            this.applicationConfigs.add(ApplicationConfig.builder()
                    .name("henvendelse")
                    .gitUrl("ssh://git@stash.devillo.no:7999/dial/henvendelse.git")
                    .build());
        } catch (Throwable t) {
            logger.error("Feil ved henting av applicationConfigs for team søknad");
        }
    }
}
