package org.example.sunsetresortwebapp.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name ="general_services")
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name ="general_services_id")
public class GeneralResortService extends ResortService {

    @Column(name ="usage_guidelines")
    private String usageGuidelines;
    private String location;

    public GeneralResortService(String usageGuidelines, String location) {
        this.usageGuidelines = usageGuidelines;
        this.location = location;
    }
    public GeneralResortService() {}

    public String getUsageGuidelines() {
        return usageGuidelines;
    }

    public void setUsageGuidelines(String usageGuidelines) {
        this.usageGuidelines = usageGuidelines;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
