package no.nav.fo.forenkletdeploy.domain;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@Wither
public class JiraIssue {
    public String id;
    public String key;
    public JiraIssueFields fields;

    @Value
    @Builder
    public static class JiraIssueFields {
        public JiraIssuePerson assignee;
        public String summary;
        public JiraIssueStatus status;
    }

    @Value
    @Builder
    public static class JiraIssuePerson {
        public String displayName;
    }

    @Value
    @Builder
    public static class JiraIssueStatus {
        public String name;
    }
}
