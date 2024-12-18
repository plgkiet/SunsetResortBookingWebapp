package org.example.sunsetresortwebapp.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservableServiceRequestDTO(
        Long reservableResortServiceId, LocalDate reservedDate, LocalTime  reservedTime, int reservedQuantity
) {
}
