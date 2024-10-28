package ru.zubcov.paymentstats.updater.okvedApiClient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zubcov.paymentstats.updater.okvedApiClient.client.OkvedApiClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class OkvedService {

    private final OkvedApiClient apiClient;
    private final ObjectMapper objectMapper;

    public String getOkvedCategory(String okved) {
        log.debug("Get category from API response");
        JsonNode root = null;
        try {
            root = objectMapper.readTree(apiClient.getOkvedCategory(okved).getBody());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        JsonNode suggestionsNode = root.path("suggestions");

        log.debug("Response body: {}", okved);
        return suggestionsNode.get(0).path("data").path("razdel").asText();
    }
}
