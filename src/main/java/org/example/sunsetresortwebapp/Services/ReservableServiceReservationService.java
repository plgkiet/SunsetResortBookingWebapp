package org.example.sunsetresortwebapp.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.PropertySource;
import org.example.sunsetresortwebapp.DTO.RequestReservationStatusDTO;
import org.example.sunsetresortwebapp.DTO.ReservableServiceRequestDTO;
import org.example.sunsetresortwebapp.DTO.ReservableServiceResponseDTO;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.ReservableResortService;
import org.example.sunsetresortwebapp.Models.ReservableServiceReservation;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Repository.ReservableServiceReservationRepository;
import org.example.sunsetresortwebapp.Utils.StatusComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ReservableServiceReservationService {
    private final ReservableServiceReservationRepository reservableServiceReservationRepository;
    @Autowired
    public ReservableServiceReservationService(ReservableServiceReservationRepository reservableServiceReservationRepository) {
        this.reservableServiceReservationRepository = reservableServiceReservationRepository;
    }
    public List<ReservableServiceReservation> findAll() {
        return reservableServiceReservationRepository.findAll();
    }


    public ReservableServiceResponseDTO  createReservableServiceReservation( ReservableResortService reservableResortService,ReservableServiceRequestDTO reservableServiceRequestDTO, User user) {
            int availableQuantity = reservableResortService.getMaximumGuests() - reservableServiceReservationRepository.getReservedNumberOfGuestsFromReservedDate(reservableServiceRequestDTO.reservedDate(), reservableResortService.getServiceId());
        ReservableServiceResponseDTO reservableServiceResponseDTO  = new ReservableServiceResponseDTO();
            if(availableQuantity < 0){
                    reservableServiceResponseDTO.setMessage("Reservation has no available guests");
                    reservableServiceResponseDTO.setSuccess(false);
            }else{
                ReservableServiceReservation reservableServiceReservation = new ReservableServiceReservation();
                reservableServiceReservation.setReservedDate(reservableServiceRequestDTO.reservedDate());
                reservableServiceReservation.setReservedQuantity(reservableServiceRequestDTO.reservedQuantity());
                reservableServiceReservation.setReservedTime(reservableServiceRequestDTO.reservedTime());
                reservableServiceReservation.setUser(user);
                reservableServiceReservation.setReservableResortService(reservableResortService);
                reservableServiceReservation.setReservationStatus(ReservationStatus.PENDING);
                reservableServiceReservationRepository.save(reservableServiceReservation);
                reservableServiceResponseDTO.setMessage("Reservation has been created successfully");
                reservableServiceResponseDTO.setSuccess(true);
            }
            reservableServiceResponseDTO.setReservableResortService(reservableResortService);
        return  reservableServiceResponseDTO;
    }
    public List<ReservableServiceReservation>  getReservableServiceReservationsByUserId(Long userId){
       return reservableServiceReservationRepository.getReservableServiceReservationsByUserId(userId);
    }
    public void updateReservableServiceReservationStautus(Long reservableServiceReservationId){
        ReservableServiceReservation r = reservableServiceReservationRepository.findById(reservableServiceReservationId).get();
        r.setReservationStatus(ReservationStatus.CANCELED);
        reservableServiceReservationRepository.save(r);
    }
    public List<ReservableServiceReservation> getAllSortedServiceReservations(){
        List<ReservableServiceReservation> reservableServiceReservations = reservableServiceReservationRepository.findAll();
        Comparator<ReservableServiceReservation> statusComparator = new StatusComparator().getStatusComparatorForReservableServiceReservation();
        reservableServiceReservations.sort(statusComparator);
        return  reservableServiceReservations;
    }
    public void updateReservableServiceReservationStatus(Long reservableServiceReservationId,ReservationStatus status){

        ReservableServiceReservation r = reservableServiceReservationRepository.findById(reservableServiceReservationId).get();
        r.setReservationStatus(status);
        reservableServiceReservationRepository.save(r);
    }
    @Transactional
    public void deleteReservableServiceReservationByUserId(Long userId){
        reservableServiceReservationRepository.deleteReservableServiceReservationsByUserId(userId);
    }
}
