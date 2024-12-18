package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=true)
@DiscriminatorValue("AccommodationImage")
public class AccommodationImage extends Image {
}
