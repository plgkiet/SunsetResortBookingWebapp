package org.example.sunsetresortwebapp.DTO;

import lombok.*;
import org.example.sunsetresortwebapp.Models.ReservableResortService;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestableResortServiceResponseDTO {
   private int id;
   private boolean isSuccess;
   private String message;
}
