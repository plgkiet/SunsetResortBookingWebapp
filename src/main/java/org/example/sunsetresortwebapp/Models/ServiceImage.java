package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=true)
@DiscriminatorValue("ServiceImage")
public class ServiceImage  extends Image{
}
