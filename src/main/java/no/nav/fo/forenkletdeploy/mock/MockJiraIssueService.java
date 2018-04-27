package no.nav.fo.forenkletdeploy.mock;

import no.nav.fo.forenkletdeploy.domain.JiraIssue;
import no.nav.fo.forenkletdeploy.service.JiraIssueService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MockJiraIssueService extends JiraIssueService {
    @Override
    public List<JiraIssue> getUserStories() {
        return Collections.emptyList();
    }

    @Override
    public JiraIssue getUserStory(String issueId) {
        JiraIssue.JiraIssuePerson person = JiraIssue.JiraIssuePerson.builder()
                .displayName("Pus the Cat")
                .build();

        JiraIssue.JiraIssueStatus status = JiraIssue.JiraIssueStatus.builder()
                .name("Under utvikling")
                .build();

        return JiraIssue.builder()
                .id(issueId)
                .key(issueId)
                .fields(JiraIssue.JiraIssueFields.builder()
                        .assignee(person)
                        .status(status)
                        .summary("En kort beskrivelse av issue")
                        .build())
                .build();
    }
}
