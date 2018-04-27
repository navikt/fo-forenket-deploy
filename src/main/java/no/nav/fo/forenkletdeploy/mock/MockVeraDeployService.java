package no.nav.fo.forenkletdeploy.mock;

import no.nav.fo.forenkletdeploy.domain.VeraDeploy;
import no.nav.fo.forenkletdeploy.service.VeraDeployService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MockVeraDeployService extends VeraDeployService {
    private static ZonedDateTime getMinutesAgo(Integer minutes) {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis() - (minutes * 60 * 1000));
        return instant.atZone(ZoneId.of("Europe/Oslo"));
    }

    private static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public List<VeraDeploy> getVeraDeploys() {
        List<VeraDeploy> deploys = new ArrayList<>();
        deploys.addAll(getDeploysForApp("pusapp"));
        deploys.addAll(getDeploysForApp("testeapp"));
        deploys.addAll(getDeploysForApp("tulleapp"));
        return deploys;
    }

    private List<VeraDeploy> getDeploysForApp(String app) {
        return Arrays.asList(
                VeraDeploy.builder()
                        .application(app)
                        .environment("t6")
                        .deployed_timestamp(getMinutesAgo(120))
                        .id(uuid())
                        .version("20181001.1010.143")
                        .build(),
                VeraDeploy.builder()
                        .application(app)
                        .environment("q6")
                        .deployed_timestamp(getMinutesAgo(60))
                        .id(uuid())
                        .version("20180910.1010.143")
                        .build(),
                VeraDeploy.builder()
                        .application(app)
                        .environment("q0")
                        .deployed_timestamp(getMinutesAgo(30))
                        .id(uuid())
                        .version("20180910.1010.143")
                        .build(),
                VeraDeploy.builder()
                        .application(app)
                        .environment("p")
                        .deployed_timestamp(getMinutesAgo(60*27))
                        .id(uuid())
                        .version("20180901.1313.1012")
                        .build()
        );
    }
}
