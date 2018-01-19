package no.nav.fo.forenkletdeploy.provider;

import no.nav.fo.forenkletdeploy.teams.FOTeam;
import no.nav.fo.forenkletdeploy.teams.Team;
import no.nav.fo.forenkletdeploy.teams.TeamOppfolging;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamProvider {

    private final List<Team> teams = new ArrayList<>();

    public TeamProvider() {
        teams.add(new TeamOppfolging());
        teams.add(new FOTeam());
    }

    public List<Team> getTeams() {
        return teams;
    }
}
