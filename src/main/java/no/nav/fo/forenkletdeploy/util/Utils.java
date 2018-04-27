package no.nav.fo.forenkletdeploy.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getRequiredProperty(String propName) {
        String envProp = System.getenv(propName);
        return envProp == null ? System.getProperty(propName, "") : envProp;
    }

    public static WebTarget withClient(String uri) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(uri);
        return webTarget;
    }

    public static <T> T fromJson(String json, Class<T> valueClass) throws IOException {
        return objectMapper.readValue(json, valueClass);
    }

    public static <T> T fromJson(InputStream json, Class<T> valueClass) throws IOException {
        return objectMapper.readValue(json, valueClass);
    }
}
