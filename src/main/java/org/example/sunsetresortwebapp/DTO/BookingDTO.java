package org.example.sunsetresortwebapp.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String roomName;
    private int quantity;
    private double price;
    private double totalPrice;
}
