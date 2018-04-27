package no.nav.fo.forenkletdeploy.teams;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static no.nav.fo.forenkletdeploy.util.Utils.fromJson;
import static no.nav.fo.forenkletdeploy.util.Utils.getRequiredProperty;
import static no.nav.fo.forenkletdeploy.util.Utils.withClient;

public class FOTeam implements Team {
    private Logger logger = LoggerFactory.getLogger(FOTeam.class.getName());

    private static List<String> IGNORED_APPLICATIONS = Arrays.asList(
            "veilarbdemo",
            "aktivitetsplan-felles",
            "badkitty"
    );

    private List<ApplicationConfig> applicationConfigs = new ArrayList<>();

    @Override
    public String getId() {
        return "fo";
    }

    @Override
    public String getDisplayName() {
        return "Forenklet Oppfølging";
    }

    @Override
    public String getJenkinsFolder() {
        return "forenklet_oppfolging";
    }

    @Override
    public List<ApplicationConfig> getApplicationConfigs() {
        return this.applicationConfigs;
    }

    @Override
    public void hentApplicationConfigs() {
        try {
            String apiToken = getRequiredProperty("GITHUB_JENKINSPUS_TOKEN");
            String configUrl = "https://raw.githubusercontent.com/navikt/jenkins-dsl-scripts/master/forenklet_oppfolging/config.json";
            String json = withClient(configUrl)
                    .request()
                    .header("Authorization", "token " + apiToken)
                    .get(String.class);
            Map<String, Map<String, String>> map = fromJson(json, Map.class);

            List<ApplicationConfig> applications = map.entrySet().stream()
                    .map(e -> ApplicationConfig.builder()
                            .name(e.getKey())
                            .gitUrl(e.getValue().get("gitUrl"))
                            .build()
                    )
                    .filter(FOTeam::applicationIsNotIgnored)
                    .collect(toList());

            if (applications != null && applications.size() > 0) {
                logger.info("Oppdaterer forenklet-oppfølging med " + applications.size() + " applikasjoner.");
                this.applicationConfigs = applications;
            }
        } catch (Throwable e) {
            logger.error("Feil ved henting av applicationConfig for FO");
        }
    }

    private static boolean applicationIsNotIgnored(ApplicationConfig applicationConfig) {
        return !IGNORED_APPLICATIONS.contains(applicationConfig.name);
    }

}
