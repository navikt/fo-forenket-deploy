package no.nav.fo.forenkletdeploy.commits.stash;

import lombok.Data;
import lombok.experimental.Accessors;
import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Commit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.nav.fo.forenkletdeploy.util.Utils.withClient;
import static no.nav.fo.forenkletdeploy.commits.stash.StashProvider.API_BASE_URL;

public class StashCommitProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(StashCommitProvider.class);
    private static final Integer LIMIT = 1000;

    private Stream<StashCommit> getCommits(ApplicationConfig application, String fromTag, String toTag) {
        String url = getRestUriForRepo(application);
        LOGGER.info(String.format("Henter commits for %s (%s -> %s)", application.name, fromTag, toTag));
        try {
            return withClient(url)
                    .queryParam("since", tagRef(fromTag))
                    .queryParam("until", tagRef(toTag))
                    .queryParam("limit", LIMIT)
                    .request()
                    .get(StashCommits.class)
                    .values.stream();
        } catch (Throwable t) {
            LOGGER.error("Feil ved henting av commits for " + application.name + " via " + url, t);
            return (new ArrayList<StashCommit>()).stream();
        }
    }

    public List<Commit> getCommitsForRelease(ApplicationConfig application, String fromTag, String toTag) {
        return this.getCommits(application, fromTag, toTag)
                .map(stashCommit -> mapToCommit(stashCommit, application))
                .collect(Collectors.toList());
    }

    public static String getRestUriForRepo(ApplicationConfig application) {
        Pattern pattern = Pattern.compile("7999/([a-zA-Z]+)/");
        Matcher matcher = pattern.matcher(application.getGitUrl());

        if(matcher.find()) {
            String project = matcher.group(1).toLowerCase();
            return String.format("%s/projects/%s/repos/%s/commits", API_BASE_URL, project, application.name);
        }
        return "";
    }

    private static String getLinkUriForCommit(ApplicationConfig application, String commit) {
        Pattern pattern = Pattern.compile("7999/([a-zA-Z]+)/");
        Matcher matcher = pattern.matcher(application.getGitUrl());

        if(matcher.find()) {
            String project = matcher.group(1).toLowerCase();
            return String.format("http://stash.devillo.no/projects/%s/repos/%s/commits/%s", project, application.name, commit);
        }
        return "";
    }

    private static String tagRef(String tag) {
        return "null".equalsIgnoreCase(tag) ? "" : "refs%2Ftags%2F" + tag;
    }

    private static String getNameForCommit(StashCommit commit) {
        return commit.author.displayName != null ? commit.author.displayName : commit.author.name;
    }

    private static Commit mapToCommit(StashCommit stashCommit, ApplicationConfig application) {
        return Commit.builder()
                .timestamp(stashCommit.committerTimestamp)
                .author(getNameForCommit(stashCommit))
                .url(getLinkUriForCommit(application, stashCommit.id))
                .message(stashCommit.message)
                .hash(stashCommit.id)
                .mergecommit(stashCommit.parents.size() > 1)
                .application(application.name)
                .build();
    }

    @Data
    @Accessors(fluent = true)
    private static class StashCommits {
        public boolean isLastPage;
        public int limit;
        public int nextPageStart;
        public int size;
        public int start;
        public List<StashCommit> values;
    }

    @Data
    @Accessors(fluent = true)
    private static class StashCommit {
        public Long authorTimestamp;
        public CommitPerson author;
        public Long committerTimestamp;
        public CommitPerson committer;
        public String displayId;
        public String id;
        public String message;
        public List<ParentCommit> parents;

    }

    @Data
    private static class CommitPerson {
        public boolean active;
        public String displayName;
        public String email;
        public int id;
        public String name;
        public String slug;
        public String type;
        public PersonLinks links;
    }

    @Data
    @Accessors(fluent = true)
    private static class PersonLinks {
        public List<PersonLink> self;
    }

    @Data
    @Accessors(fluent = true)
    private static class PersonLink {
        public String href;
    }

    @Data
    @Accessors(fluent = true)
    private static class ParentCommit {
        public String displayId;
        public String id;
    }
}
