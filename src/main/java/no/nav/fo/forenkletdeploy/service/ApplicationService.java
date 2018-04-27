package no.nav.fo.forenkletdeploy.service;

import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.provider.TeamProvider;
import no.nav.fo.forenkletdeploy.teams.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationService {

    private final TeamProvider teamProvider;
    private static final long MINUTTER = 1000 * 60;
    private Logger logger = LoggerFactory.getLogger(ApplicationService.class.getName());

    @Inject
    public ApplicationService(TeamProvider teamProvider) {
        this.teamProvider = teamProvider;
    }

    @SuppressWarnings("unchecked")
    @Cacheable("applicationlist")
    public List<ApplicationConfig> getApps() {
        return getAllAppConfigurations();
    }

    private List<ApplicationConfig> getAllAppConfigurations() {
        return teamProvider.getTeams().stream()
                .flatMap(team -> team.getApplicationConfigs().stream())
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Cacheable("applicationlistbyteam")
    public List<ApplicationConfig> getAppsByTeam(String teamId) {
        return teamProvider.getTeams().stream()
                .filter(team -> team.getId().equals(teamId))
                .flatMap(team -> team.getApplicationConfigs().stream())
                .collect(Collectors.toList());
    }

    @Cacheable("appbyname")
    public ApplicationConfig getAppByName(String name) {
        return getAllAppConfigurations().stream()
                .filter(app -> app.name.equalsIgnoreCase(name))
                .findFirst()
                .get();
    }

    @Scheduled(fixedRate = 10 * MINUTTER)
    public void lastAlleApplicationConfigs() {
        logger.info("Henter og oppdaterer alle applicationConfigs");
        teamProvider.getTeams().forEach(Team::hentApplicationConfigs);
    }

    public List<ApplicationConfig> getLibrariesByTeam(String teamId) {
        return getAppsByTeam(teamId).stream()
                .filter(app -> app.library)
                .collect(Collectors.toList());
    }

}

