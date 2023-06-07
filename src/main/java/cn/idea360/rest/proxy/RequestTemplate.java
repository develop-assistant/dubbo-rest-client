package cn.idea360.rest.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * @author cuishiying
 */
@Slf4j
public class RequestTemplate {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();
    private final String url;

    public RequestTemplate(String url) {
        this.url = url;
    }

    public Object doRequest(ProxyData proxyData, Type type) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.of(15, SECONDS))
                    .header("Accept", "application/json").header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers
                            .ofString(objectMapper.writeValueAsString(proxyData)))
                    .build();
            Class<?> returnType = ClassUtils.resolveClassName(type.getTypeName(), null);
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 && !"404".equals(response.body())) {
                log.info("response: {}", response.body());
                return objectMapper.readValue(response.body(), returnType);
            }
        }
        catch (Exception e) {
            log.error(String.format("proxy err. %s", proxyData), e);
        }
        return null;
    }

}
