package no.nav.fo.forenkletdeploy.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApplicationConfig {
    public String name;
    public String gitUrl;
    public boolean library;
}
