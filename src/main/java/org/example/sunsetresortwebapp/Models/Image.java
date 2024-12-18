package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.*;

@Entity
@Table(name ="images")
@Inheritance(strategy =InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="image_type")
public class Image {
  @Id
  @GeneratedValue
  private long imageId;
  @Column(name ="image_url")
  private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Image() {}
    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToOne
    @JoinColumn(name ="accommodation_id")
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name ="service_id")
    private ResortService resortService;

  public Accommodation getAccommodation() {
    return accommodation;
  }

  public void setAccommodation(Accommodation accommodation) {
    this.accommodation = accommodation;
  }

  public ResortService getResortService() {
    return resortService;
  }

  public void setResortService(ResortService resortService) {
    this.resortService = resortService;
  }
}
