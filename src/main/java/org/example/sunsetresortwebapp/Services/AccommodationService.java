package org.example.sunsetresortwebapp.Services;

import org.example.sunsetresortwebapp.DTO.AccommodationSearchingDTO;
import org.example.sunsetresortwebapp.Models.Accommodation;
import org.example.sunsetresortwebapp.Repository.AccommodationRepository;
import org.example.sunsetresortwebapp.Repository.AccommodationReservationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }
    public Accommodation  createAccommodation(Accommodation accommodation){
//        Accommodation accommodation = new Accommodation();
//        accommodation.setAccommodationType(type);
//        accommodation.setAmentities(amentities);
//        accommodation.setAvailableQuantity(availableQuantity);
//        accommodation.setPricePerNight(pricePerNight);
//        accommodation.setMaximumGuests(maximumGuests);
        return accommodationRepository.save(accommodation);
    };
    public Accommodation getAccommodationById(Long id) {
        return accommodationRepository.findByAccommodationId(id);
    }
    public List<Accommodation> getAccommodationWithImages(){
        return accommodationRepository.findAccommodationWithAllImages();
    }
    public List<Accommodation> filterAccommodationWithNumberOfGuests(int numberOfGuests){
        List<Accommodation> accommodations = this.getAllAccommodations().stream().filter((accommodation) -> accommodation.getMaximumGuests() >= numberOfGuests).collect(Collectors.toList());
        return accommodations;
    }
}
