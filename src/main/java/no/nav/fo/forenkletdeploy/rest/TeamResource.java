package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.Team;
import no.nav.fo.forenkletdeploy.service.TeamService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamResource {

    private final TeamService teamService;

    @Inject
    public TeamResource(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> getTeams() {
        return teamService.getTeams();
    }

}
