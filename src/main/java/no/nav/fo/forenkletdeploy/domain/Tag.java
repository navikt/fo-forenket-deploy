package no.nav.fo.forenkletdeploy.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Tag {
    String displayId;
    String latestCommit;
    String application;
}
