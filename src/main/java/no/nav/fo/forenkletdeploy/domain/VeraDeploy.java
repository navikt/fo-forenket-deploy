package no.nav.fo.forenkletdeploy.domain;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Value
@Builder
public class VeraDeploy {
    public String id;
    public String application;
    public ZonedDateTime deployed_timestamp;
    public String version;
    public String environment;

    public static class VeraDeploys extends ArrayList<VeraDeploy> {
    }
}
