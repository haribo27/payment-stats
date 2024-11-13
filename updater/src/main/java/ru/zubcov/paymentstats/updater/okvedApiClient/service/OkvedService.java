package ru.zubcov.paymentstats.updater.okvedApiClient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.zubcov.paymentstats.updater.exception.UnexpectedApiResponseError;
import ru.zubcov.paymentstats.updater.okvedApiClient.client.OkvedApiClient;
import ru.zubcov.paymentstats.updater.okvedApiClient.dto.SuggestionResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class OkvedService {

    private final OkvedApiClient apiClient;

    @Cacheable(value = "okvedCategoryCache", key = "#okvedId")
    public String getOkvedCategory(String okvedId) {
        String razdel = null;
        log.debug("Get category from API response");
        String query = "{\"query\": \"" + okvedId + "\"}";
        ResponseEntity<SuggestionResponseDto> response = apiClient.getOkvedCategory(query);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Response code: 200 OK, response {}", response);
            SuggestionResponseDto suggestionResponseDto = response.getBody();
            if (suggestionResponseDto.getSuggestions() != null && !suggestionResponseDto.getSuggestions().isEmpty()
                    && suggestionResponseDto.getSuggestions().getFirst().getData() != null) {
                razdel = suggestionResponseDto.getSuggestions().getFirst().getData().getRazdel();
                log.info("Razdel: {}", razdel);
            } else {
                throw new UnexpectedApiResponseError("Some of response fields = null");
            }
        } else {
            log.warn("Request status: {}", response.getStatusCode());
            throw new UnexpectedApiResponseError("Response status:" + response.getStatusCode());
        }
        log.info("Razdel is got successfully");
        return razdel;
    }

}
