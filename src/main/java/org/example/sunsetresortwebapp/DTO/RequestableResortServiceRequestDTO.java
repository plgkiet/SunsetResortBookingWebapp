package org.example.sunsetresortwebapp.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestableResortServiceRequestDTO(
        Long requestableResortServiceId, String roomName, LocalDate requestDate, LocalTime requestTime
) {
}
