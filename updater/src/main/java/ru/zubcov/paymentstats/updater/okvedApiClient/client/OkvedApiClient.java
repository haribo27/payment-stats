package ru.zubcov.paymentstats.updater.okvedApiClient.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OkvedApiClient {

    @Value("${okved.api-key}")
    private String apiKey;
    private static final String OKVED_FIND_BY_ID_URL =
            "http://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/okved2";

    private final RestTemplate restTemplate;

    public ResponseEntity<String> getOkvedCategory(String okvedId) {
        String query = "{\"query\": \"" + okvedId + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(query, defaultHeaders());

        return restTemplate.exchange(OKVED_FIND_BY_ID_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);
        return headers;
    }
}
