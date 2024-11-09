package ru.zubcov.paymentstats.stats.exception;

import lombok.Data;

import java.time.Instant;

@Data
public final class ErrorResponse {

    private final String status;
    private final String message;
    private final Instant timestamp;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }
}
