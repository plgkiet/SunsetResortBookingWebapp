package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name ="accommodation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue
    private Long accommodationId;
    @Column(name="name")
    private String accommodationName;
    @Column(name ="quantity")
    private Integer availableQuantity;
    @Column(name ="price")
    private Double pricePerNight;
    private String description;
    private String amenities;
    private int maximumGuests;

    @OneToMany(
            mappedBy = "accommodation",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    List<Image> images;

    @OneToMany(
            mappedBy = "accommodation",
            cascade =  CascadeType.ALL,
            fetch =  FetchType.EAGER
    )
    List<AccommodationReservationDetail>    accommodationReservationDetails;



}
