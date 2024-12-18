package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name ="requestable_services")
@PrimaryKeyJoinColumn(name ="requestable_services_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestableResortService extends ResortService {
    private String requestName;
    private String requestDescription;

    @OneToMany(
            mappedBy = "requestableResortService",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    List<RequestableServiceRequest> requestableServiceRequests;
}