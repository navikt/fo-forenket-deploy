package no.nav.fo.forenkletdeploy.teams;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static no.nav.fo.forenkletdeploy.util.Utils.fromJson;
import static no.nav.fo.forenkletdeploy.util.Utils.withClient;

public class TeamOppfolging implements Team {
    private Logger logger = LoggerFactory.getLogger(TeamOppfolging.class.getName());
    public static final String APPLICATION_CONFIG_URL = "http://stash.devillo.no/projects/OPP/repos/team-oppfolging/raw/applikasjonsportefolje/config.json";

    private List<ApplicationConfig> applicationConfigs = new ArrayList<>();

    @Override
    public String getId() {
        return "teamoppfolging";
    }

    @Override
    public String getDisplayName() {
        return "Team Oppfølging";
    }

    @Override
    public String getJenkinsFolder() {
        return "teamoppfolging";
    }

    @Override
    public List<ApplicationConfig> getApplicationConfigs() {
        return this.applicationConfigs;
    }

    @Override
    public void hentApplicationConfigs() {
        try {
            String json = withClient(APPLICATION_CONFIG_URL).request().get(String.class);
            Map<String, Map<String, Object>> map = fromJson(json, Map.class);
            this.applicationConfigs = map.entrySet().stream()
                    .map(entry -> ApplicationConfig.builder()
                            .name(entry.getKey())
                            .gitUrl((String) entry.getValue().get("gitUrl"))
                            .library((Boolean) entry.getValue().getOrDefault("library", Boolean.FALSE))
                            .build()
                    )
                    .collect(toList());
        } catch (Throwable e) {
            logger.error("Feil ved henting av application-configs for team-oppfølging");
        }
    }
}
