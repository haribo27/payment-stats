package ru.zubcov.paymentstats.updater.okvedApiClient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SuggestionResponseDto {

    @JsonProperty("suggestions")
    private List<Suggestion> suggestions;

    @Data
    public static final class Suggestion {

        @JsonProperty("value")
        private String value;

        @JsonProperty("unrestricted_value")
        private String unrestrictedValue;

        @JsonProperty("data")
        private OkvedData data;
    }

    @Data
    public static final class OkvedData {

        @JsonProperty("idx")
        private String idx;

        @JsonProperty("razdel")
        private String razdel;

        @JsonProperty("kod")
        private String kod;

        @JsonProperty("name")
        private String name;
    }
}
