package ru.zubcov.paymentstats.updater.okvedApiClient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class SuggestionResponseDto {

    @JsonProperty("suggestions")
    List<Suggestion> suggestions;

    @Value
    public static class Suggestion {

        @JsonProperty("value")
        String value;

        @JsonProperty("unrestricted_value")
        String unrestrictedValue;

        @JsonProperty("data")
        OkvedData data;
    }

    @Value
    public static class OkvedData {

        @JsonProperty("idx")
        String idx;

        @JsonProperty("razdel")
        String razdel;

        @JsonProperty("kod")
        String kod;

        @JsonProperty("name")
        String name;
    }
}
