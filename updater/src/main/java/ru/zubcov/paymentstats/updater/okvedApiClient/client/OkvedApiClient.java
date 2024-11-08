package ru.zubcov.paymentstats.updater.okvedApiClient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.zubcov.paymentstats.updater.okvedApiClient.config.FeignConfig;
import ru.zubcov.paymentstats.updater.okvedApiClient.dto.SuggestionResponseDto;

@FeignClient(name = "okvedApiClient", url = "${okved.api-url}", configuration = FeignConfig.class)
public interface OkvedApiClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SuggestionResponseDto> getOkvedCategory(@RequestBody String query);
}