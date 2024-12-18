package org.example.sunsetresortwebapp.Services;

import jakarta.transaction.Transactional;
import org.example.sunsetresortwebapp.DTO.AccommodationReservationDTO;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.Accommodation;
import org.example.sunsetresortwebapp.Models.AccommodationReservation;
import org.example.sunsetresortwebapp.Models.AccommodationReservationDetail;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Repository.AccommodationRepository;
import org.example.sunsetresortwebapp.Repository.AccommodationReservationDetailRepository;
import org.example.sunsetresortwebapp.Repository.AccommodationReservationRepository;
import org.example.sunsetresortwebapp.Repository.UserRepository;
import org.example.sunsetresortwebapp.Utils.StatusComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class AccommodationReservationService {
    private final AccommodationReservationRepository accommodationReservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationReservationDetailRepository accommodationReservationDetailRepository;

    @Autowired
    public AccommodationReservationService(AccommodationReservationRepository accommodationReservationRepository, UserRepository userRepository, AccommodationRepository accommodationRepository, AccommodationReservationDetailRepository accommodationReservationDetailRepository) {
        this.accommodationReservationRepository = accommodationReservationRepository;
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.accommodationReservationDetailRepository = accommodationReservationDetailRepository;
    }
   public List<AccommodationReservation>  getAllReservationsByUserId(User user){
        return accommodationReservationRepository.findAccommodationReservationsByUserId(user.getId());
   }
   public void updateAccommodationReservation(AccommodationReservation accommodationReservation){
        accommodationReservationRepository.save(accommodationReservation);
   }
   public AccommodationReservation getAccommodationReservation(Long accommodationReservationId){
        return accommodationReservationRepository.findById(accommodationReservationId).get();
   }
    public void makeReservations(User user,AccommodationReservationDTO accommodationReservationDTO) {
        AccommodationReservation accommodationReservation = new AccommodationReservation();
        accommodationReservation.setUser(user);
        accommodationReservation.setStatus(ReservationStatus.PENDING);
        accommodationReservation.setTotalPrice(accommodationReservationDTO.totalPrice());
        accommodationReservation.setTotalQuantity(accommodationReservationDTO.totalQuantity());
        accommodationReservation.setCheckInDate(LocalDate.parse(formatDate(accommodationReservationDTO.checkInDate())));
        accommodationReservation.setCheckOutDate(LocalDate.parse(formatDate(accommodationReservationDTO.checkOutDate())));
        accommodationReservationRepository.save(accommodationReservation);
        accommodationReservationDTO.reservationUnitDTOList().forEach((reservationUnitDTO) -> {
               Accommodation accommodation = accommodationRepository.findById(reservationUnitDTO.getAccommodationId()).orElse(null);
               AccommodationReservationDetail accommodationReservationDetail = new AccommodationReservationDetail();
               accommodationReservationDetail.setAccommodationReservation(accommodationReservation);
               accommodationReservationDetail.setReservedQuantity(reservationUnitDTO.getQuantity());
               accommodationReservationDetail.setAccommodation(accommodation);
               accommodationReservationDetail.setAccommodationReservationTotalPrice(reservationUnitDTO.getTotalPrice());
               accommodationReservationDetailRepository.save(accommodationReservationDetail);
        });
    }
    public void updateAccommodationReservationStatus(Long accommodationReservationId, ReservationStatus status){
        AccommodationReservation ar = accommodationReservationRepository.findById(accommodationReservationId).get();
        ar.setStatus(status);
        accommodationReservationRepository.save(ar);
    }
    public String formatDate(String date){
        String [] dates = date.split("/");
        return dates[2] + "-" + dates[0] + "-" + dates[1];
    }
    public List<AccommodationReservation> getAllAccommodationReservations(){
        return accommodationReservationRepository.findAll();
    }

    public List<AccommodationReservation> getAllSortedReservations(){
        List<AccommodationReservation> accommodationReservations  = accommodationReservationRepository.findAll();
        Comparator<AccommodationReservation> statusComparator = new StatusComparator().getStatusComparatorForAccommodationReservation();
        accommodationReservations.sort(statusComparator);
        return accommodationReservations;
    }
    @Transactional
    public void deleteAccommodationReservationByUserId(Long userId){
        accommodationReservationRepository.deleteAccommodationReservationsByUserId(userId);
    }


}
