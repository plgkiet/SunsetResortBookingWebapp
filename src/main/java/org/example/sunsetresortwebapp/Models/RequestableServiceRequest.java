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
@Table(name  = "requestable_service_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestableServiceRequest {
    @Id
    @GeneratedValue
    private Long requestableServiceRequestId;

    private String roomName;
    private LocalDate  requestDate;
    private LocalTime requestTime;

    @ManyToOne
    @JoinColumn(name  = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "requestable_resort_service_id")
    private RequestableResortService requestableResortService;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
