package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accommodation_reservation_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationReservationDetail {
    @Id
    @GeneratedValue
    private long accommodationReservationDetailId;
    @Column(name ="reserved_quantity")
    private int reservedQuantity;
    @Column(name ="accommodation_reservation_detail_total_price" , nullable = true)
    private Double accommodationReservationTotalPrice;
    @ManyToOne
    @JoinColumn(name ="accommodation_reservation_id")
    private AccommodationReservation accommodationReservation;
    @ManyToOne
    @JoinColumn(name ="accommodation_id")
    private Accommodation accommodation;
}
