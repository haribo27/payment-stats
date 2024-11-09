package ru.zubcov.paymentstats.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.zubcov.paymentstats.stats.util.StartBeforeEndDateValid;

import java.time.LocalDateTime;

@Data
@StartBeforeEndDateValid
public final class ClientRequestStatsDto {

    @NotNull(message = "Id клиента не может быть null")
    private final Long clientId;
    @NotNull(message = "Дата начала периода статистики платежей не может быть null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endDate;
}
