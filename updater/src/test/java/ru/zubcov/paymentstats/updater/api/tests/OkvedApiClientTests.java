package ru.zubcov.paymentstats.updater.api.tests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ru.zubcov.paymentstats.updater.BaseTestContainerClass;
import ru.zubcov.paymentstats.updater.okvedApiClient.client.OkvedApiClient;
import ru.zubcov.paymentstats.updater.okvedApiClient.dto.SuggestionResponseDto;

import java.util.List;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@WireMockTest(httpPort = 8080)
public class OkvedApiClientTests extends BaseTestContainerClass {

    @Autowired
    private OkvedApiClient okvedApiClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${okved.api-key}")
    private String apikey;

    @Test
    public void executeRequestSuccess() throws JsonProcessingException {
        SuggestionResponseDto response = new SuggestionResponseDto(List.of(new SuggestionResponseDto.Suggestion(
                "1", "2", new SuggestionResponseDto.OkvedData("3", "H", "5", "6"))));
        String query = "{\"query\": \"30\"}";
        stubFor(post(urlEqualTo("/suggestions/api/4_1/rs/findById/okved2"))
                .withRequestBody(equalToJson(query))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(response))));

        ResponseEntity<SuggestionResponseDto> result = okvedApiClient.getOkvedCategory(query);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("H", Objects.requireNonNull(result.getBody()).getSuggestions().getFirst().getData().getRazdel());
    }

    @Test
    public void executeRequestFailWithInvalidApiKey() {
        String query = "{\"query\": \"30\"}";
        stubFor(post(urlEqualTo("/suggestions/api/4_1/rs/findById/okved2"))
                .withRequestBody(equalToJson(query))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ error: invalid_token, message: The access token expired or is invalid }")));  // Тело ответа
        try {
            okvedApiClient.getOkvedCategory(query);
        } catch (FeignException e) {
            Assertions.assertEquals(401, e.status());
        }
    }

    @Test
    public void executeRequestBadRequest() {
        String query = "{\"query\": \"sadfsadfsdf\"}";
        stubFor(post(urlEqualTo("/suggestions/api/4_1/rs/findById/okved2"))
                .withRequestBody(equalToJson(query))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")));
        try {
            okvedApiClient.getOkvedCategory(query);
        } catch (FeignException e) {
            Assertions.assertEquals(400, e.status());
        }
    }

    @Test
    public void executeRequestServerInternalError() {
        String query = "{\"query\": \"30\"}";
        stubFor(post(urlEqualTo("/suggestions/api/4_1/rs/findById/okved2"))
                .withHeader("Authorization", equalTo("Token " + apikey))
                .withRequestBody(equalToJson(query))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")));
        try {
            okvedApiClient.getOkvedCategory(query);
        } catch (FeignException e) {
            Assertions.assertEquals(500, e.status());
        }
    }
}

