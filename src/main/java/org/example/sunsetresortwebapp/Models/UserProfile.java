package org.example.sunsetresortwebapp.Models;


import jakarta.persistence.*;

@Entity
@Table(name="profiles")
public class UserProfile {
    @Id
    @GeneratedValue
    private Long id;


    @Column(name ="fullname", length = 255)
    private String fullname;
    private String address;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name ="user_id")
    private User user;
    public UserProfile(){}

    public UserProfile(String fullname, String address, String phoneNumber) {
        this.fullname = fullname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
