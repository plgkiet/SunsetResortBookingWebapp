package org.example.sunsetresortwebapp.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationUnitDTO {
    private Long accommodationId;
    private String accommodationName;
    private int quantity;
    private double price;
    private double totalPrice;
}
