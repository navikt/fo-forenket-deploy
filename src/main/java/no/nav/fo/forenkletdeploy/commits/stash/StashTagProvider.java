package no.nav.fo.forenkletdeploy.commits.stash;

import lombok.Data;
import lombok.experimental.Accessors;
import no.nav.fo.forenkletdeploy.domain.ApplicationConfig;
import no.nav.fo.forenkletdeploy.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.nav.fo.forenkletdeploy.commits.stash.StashProvider.API_BASE_URL;
import static no.nav.fo.forenkletdeploy.util.Utils.withClient;

@Component
public class StashTagProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(StashCommitProvider.class);
    private static final Integer LIMIT = 1000;

    public List<Tag> getTagsForApplication(ApplicationConfig applicationConfig) {
        return this.getTags(applicationConfig)
                .map(stashTag -> mapToTag(stashTag, applicationConfig))
                .collect(Collectors.toList());
    }

    private Stream<StashTag> getTags(ApplicationConfig application) {
        String url = getRestUriForRepo(application);
        LOGGER.info(String.format("Henter tags for %s", application.name));
        try {
            return withClient(url)
                    .queryParam("limit", LIMIT)
                    .request()
                    .get(StashTags.class)
                    .values.stream();
        } catch (Throwable t) {
            LOGGER.error("Feil ved henting av commits for " + application.name + " via " + url, t);
            return (new ArrayList<StashTag>()).stream();
        }
    }

    private static String getRestUriForRepo(ApplicationConfig application) {
        Pattern pattern = Pattern.compile("7999/([a-zA-Z]+)/");
        Matcher matcher = pattern.matcher(application.getGitUrl());

        if(matcher.find()) {
            String project = matcher.group(1).toLowerCase();
            return String.format("%s/projects/%s/repos/%s/tags", API_BASE_URL, project, application.name);
        }
        return "";
    }


    private static Tag mapToTag(StashTag stashTag, ApplicationConfig applicationConfig) {
        return Tag.builder()
                .displayId(stashTag.displayId)
                .latestCommit(stashTag.latestCommit)
                .application(applicationConfig.getName())
                .build();
    }
    @Data
    @Accessors(fluent = true)
    private static class StashTags {
        public boolean isLastPage;
        public int limit;
        public int nextPageStart;
        public int size;
        public int start;
        public List<StashTag> values;
    }

    @Data
    @Accessors(fluent = true)
    private static class StashTag {
        public String displayId;
        public String id;
        public String latestCommit;
        public String latestChangeset;
    }

}
