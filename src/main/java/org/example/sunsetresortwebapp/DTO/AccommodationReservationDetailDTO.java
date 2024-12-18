package org.example.sunsetresortwebapp.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationReservationDetailDTO {
    private Long accommodationReservationDetailId;
    private Integer reservedQuantity;
    private Double accommodationReservationTotalPrice;  // if applicable
    private String accommodationReservationName;


}
