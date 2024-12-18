package org.example.sunsetresortwebapp.DTO;

import lombok.*;
import org.example.sunsetresortwebapp.Models.ReservableResortService;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservableServiceResponseDTO {
   private int id;
   private ReservableResortService reservableResortService;
   private boolean isSuccess;
   private String message;
}
