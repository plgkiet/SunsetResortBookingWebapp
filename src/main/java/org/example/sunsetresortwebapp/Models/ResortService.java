package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name ="services")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResortService {
    @Id
    @GeneratedValue
    private Long serviceId;
    @Column(name ="price")
    private Double price;
    @Column(name ="description")
    private String description;
   private String location;
    private LocalTime openHours;
    private LocalTime closeHours;
    @OneToMany(
            mappedBy = "resortService",
            cascade = CascadeType.ALL
    )
    List<Image> images;



}
