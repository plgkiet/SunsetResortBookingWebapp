package org.example.sunsetresortwebapp.Repository;

import jakarta.transaction.Transactional;
import org.example.sunsetresortwebapp.Models.ReservableServiceReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservableServiceReservationRepository extends JpaRepository<ReservableServiceReservation, Long> {
    @Query("SELECT COALESCE(SUM(rsr.reservedQuantity), 0) " +
            "FROM ReservableServiceReservation rsr " +
            "WHERE rsr.reservedDate = :reservedDate AND rsr.reservableResortService.serviceId = :serviceId" )
    int getReservedNumberOfGuestsFromReservedDate(@Param("reservedDate") LocalDate reservedDate,
                                                  @Param("serviceId") Long reservableResortServiceId);
    @Query("SELECT r from ReservableServiceReservation r where r.user.id = :userId  ")

    List<ReservableServiceReservation> getReservableServiceReservationsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM  ReservableServiceReservation rsr where rsr.user.id = :userId")
    void deleteReservableServiceReservationsByUserId(@Param("userId") Long userId);
}
