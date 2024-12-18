package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservable_service-reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservableServiceReservation {
    @Id
    @GeneratedValue
    private Long reservableServiceReservationId;
    private LocalDate reservedDate;
    private LocalTime reservedTime;
    private Integer reservedQuantity;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    @ManyToOne
    @JoinColumn(name ="reservable_resort_service_id")
    private ReservableResortService reservableResortService;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
}
