package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "accommodation_reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationReservation {
    @Id
    @GeneratedValue
    @Column(name ="accommodation_reservation_id")
    private Long accommodationReservationId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @Column(length = 255)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @Column(name ="total_price")
    private double totalPrice;
    @Column(name ="quantity")
    private int totalQuantity;
    @Column(name ="special_request" , nullable = true)
    private String specialRequest;
    @Column(name ="length_of_stays")
    private int lengthOfStays;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
    @OneToMany(
            mappedBy = "accommodationReservation",
            cascade =  CascadeType.ALL,
            fetch =  FetchType.EAGER,
            orphanRemoval = true
    )
    private List<AccommodationReservationDetail> accommodationReservationDetails;


}
