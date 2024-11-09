package ru.zubcov.paymentstats.updater.okvedApiClient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public final class SuggestionResponseDto {

    @JsonProperty("suggestions")
    private final List<Suggestion> suggestions;

    @Data
    public static final class Suggestion {

        @JsonProperty("value")
        private final String value;

        @JsonProperty("unrestricted_value")
        private final String unrestrictedValue;

        @JsonProperty("data")
        private final OkvedData data;
    }

    @Data
    public static final class OkvedData {

        @JsonProperty("idx")
        private final String idx;

        @JsonProperty("razdel")
        private final String razdel;

        @JsonProperty("kod")
        private final String kod;

        @JsonProperty("name")
        private final String name;
    }
}
