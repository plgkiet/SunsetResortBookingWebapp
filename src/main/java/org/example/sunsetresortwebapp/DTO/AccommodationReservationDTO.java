package org.example.sunsetresortwebapp.DTO;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public record AccommodationReservationDTO(
      String checkInDate, String checkOutDate,Double totalPrice, Integer totalQuantity, int lengthOfStay, List<ReservationUnitDTO> reservationUnitDTOList
) {
}
