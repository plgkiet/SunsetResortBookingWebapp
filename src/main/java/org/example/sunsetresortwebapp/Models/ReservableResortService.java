package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name ="reservable_services")
@PrimaryKeyJoinColumn(name ="reservable_service_id")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservableResortService extends ResortService {
    @Column(name ="maximum_guests")
    private  int maximumGuests;

    @Column(name = "reservable_service_name")
    private String reservableServiceName;

    @OneToMany(
            mappedBy = "reservableResortService",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<ReservableServiceReservation> reservableServiceReservation;

}
