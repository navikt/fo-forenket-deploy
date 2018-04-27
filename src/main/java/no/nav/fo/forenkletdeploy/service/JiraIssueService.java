package no.nav.fo.forenkletdeploy.service;

import no.nav.fo.forenkletdeploy.domain.JiraIssue;
import no.nav.fo.forenkletdeploy.domain.JiraIssues;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static no.nav.fo.forenkletdeploy.util.Utils.withClient;

@Component
public class JiraIssueService {

    // https://jira.adeo.no/secure/RapidBoard.jspa?rapidView=11947
    private static final List<String> IKKE_INTERESSANTE_STATUSER = Arrays.asList(
            "Produktkø",
            "Analyse",
            "Verifisering",
            "Klar til utvikling",
            "Ferdig"
    );
    public static final String IKKE_INTERESSANTE_STATUSER_FORMATTERT = IKKE_INTERESSANTE_STATUSER.stream().map(s -> "'" + s + "'").collect(Collectors.joining(","));
    public static final String FO_JQL = "project=fo AND status not in (" + IKKE_INTERESSANTE_STATUSER_FORMATTERT + ")";

    @SuppressWarnings("unchecked")
    @Cacheable("jiraissues")
    public List<JiraIssue> getUserStories() {
        return withClient("https://jira.adeo.no/rest/api/2/search")
                .queryParam("jql", FO_JQL)
                .queryParam("maxResults", 500)
                .request()
                .get(JiraIssues.class)
                .issues;
    }

    @SuppressWarnings("unchecked")
    @Cacheable("jiraissue")
    public JiraIssue getUserStory(String issueId) {
        return withClient("https://jira.adeo.no/rest/api/2/issue/" + issueId)
                .request()
                .get(JiraIssue.class);
    }
}

