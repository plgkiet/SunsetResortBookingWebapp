package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sunsetresortwebapp.Enum.UserRole;
import org.example.sunsetresortwebapp.Enum.UserStatus;
import org.example.sunsetresortwebapp.Services.RequestableResortServiceService;

import java.util.List;


@Entity
@Table(name ="users")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole = UserRole.USER;
    private String salt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status = UserStatus.ACTIVATED;
    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private UserProfile profile;


    @OneToMany(
            mappedBy =  "user",
            cascade =  CascadeType.ALL,
            fetch =  FetchType.EAGER,
            orphanRemoval = true
    )
    List<AccommodationReservation> accommodationReservations;

    @OneToMany(
            mappedBy =  "user",
            cascade =  CascadeType.ALL,
            fetch =  FetchType.EAGER,
            orphanRemoval = true
    )
    List<ReservableServiceReservation> reservableServiceReservations;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    List<RequestableServiceRequest>  requestableServiceRequests;

}
