package no.nav.fo.forenkletdeploy.rest;

import no.nav.fo.forenkletdeploy.domain.JiraIssue;
import no.nav.fo.forenkletdeploy.domain.JiraIssues;
import no.nav.fo.forenkletdeploy.service.JiraIssueService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/jira")
public class JiraIssueResource {
    private final JiraIssueService jiraIssueService;

    @Inject
    JiraIssueResource(JiraIssueService jiraIssueService) {
        this.jiraIssueService = jiraIssueService;
    }

    @GetMapping("/")
    public List<JiraIssue> getAllFoIssues() {
        return jiraIssueService.getUserStories();
    }

    @GetMapping("/{issueid}")
    public JiraIssue getJiraIssue(@PathVariable("issueid") String issueid) {
        return jiraIssueService.getUserStory(issueid)
                .withKey(issueid);
    }
}
