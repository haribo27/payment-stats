package ru.zubcov.paymentstats.updater.okved.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.zubcov.paymentstats.updater.exception.UnexpectedApiResponseError;
import ru.zubcov.paymentstats.updater.okvedApiClient.client.OkvedApiClient;
import ru.zubcov.paymentstats.updater.okvedApiClient.dto.SuggestionResponseDto;
import ru.zubcov.paymentstats.updater.okvedApiClient.service.OkvedService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OkvedServiceTests {

    @Mock
    private OkvedApiClient okvedApiClient;

    @InjectMocks
    private OkvedService okvedService;

    @Test
    public void testNullResponseFromClient() {
        ResponseEntity<SuggestionResponseDto> response = new ResponseEntity<>(new SuggestionResponseDto(Collections.emptyList()), HttpStatus.OK);
        when(okvedApiClient.getOkvedCategory(anyString())).thenReturn(response);

        assertThrows(UnexpectedApiResponseError.class, () -> okvedService.getOkvedCategory("{\"query\": \"51\"}"));
    }

    @Test
    public void test404ResponseStatus() {

        ResponseEntity<SuggestionResponseDto> response = new ResponseEntity<>(
                new SuggestionResponseDto(Collections.emptyList()), HttpStatus.NOT_FOUND);
        when(okvedApiClient.getOkvedCategory(anyString())).thenReturn(response);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertThrows(UnexpectedApiResponseError.class, () -> okvedService.getOkvedCategory("{\"query\": \"51\"}"));
    }

    @Test
    public void testSuccessGetOkvedCategory() {
        SuggestionResponseDto suggestions = new SuggestionResponseDto(List.of(
                new SuggestionResponseDto.Suggestion("1", "2",
                        new SuggestionResponseDto.OkvedData("3", "H", "5", "6"))));
        ResponseEntity<SuggestionResponseDto> response = new ResponseEntity<>(suggestions, HttpStatus.OK);
        when(okvedApiClient.getOkvedCategory(anyString())).thenReturn(response);
        String category = okvedService.getOkvedCategory("{\"query\": \"51\"}");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("H", category);
    }
}
