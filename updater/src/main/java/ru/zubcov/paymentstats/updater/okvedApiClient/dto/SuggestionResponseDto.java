package ru.zubcov.paymentstats.updater.okvedApiClient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class SuggestionResponseDto {
    @JsonProperty("suggestions")
    List<Suggestion> suggestions;

    @JsonCreator
    public SuggestionResponseDto(@JsonProperty("suggestions") List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    @Value
    public static class Suggestion {
        @JsonProperty("value")
        String value;
        @JsonProperty("unrestricted_value")
        String unrestrictedValue;
        @JsonProperty("data")
        OkvedData data;

        @JsonCreator
        public Suggestion(@JsonProperty("value") String value,
                          @JsonProperty("unrestricted_value") String unrestrictedValue,
                          @JsonProperty("data") OkvedData data) {
            this.value = value;
            this.unrestrictedValue = unrestrictedValue;
            this.data = data;
        }
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

        @JsonCreator
        public OkvedData(@JsonProperty("idx") String idx,
                         @JsonProperty("razdel") String razdel,
                         @JsonProperty("kod") String kod,
                         @JsonProperty("name") String name) {
            this.idx = idx;
            this.razdel = razdel;
            this.kod = kod;
            this.name = name;
        }
    }
}
