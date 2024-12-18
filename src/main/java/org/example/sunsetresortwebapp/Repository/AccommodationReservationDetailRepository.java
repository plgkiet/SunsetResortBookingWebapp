package org.example.sunsetresortwebapp.Repository;

import jakarta.transaction.Transactional;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.AccommodationReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface AccommodationReservationDetailRepository extends JpaRepository<AccommodationReservationDetail, Long> {
    @Query("SELECT COALESCE(SUM(d.reservedQuantity), 0) " +
            "FROM AccommodationReservationDetail d " +
            " JOIN d.accommodationReservation ar ON d.accommodationReservation.accommodationReservationId = ar.accommodationReservationId " +
            "WHERE d.accommodation.accommodationId = :accommodationId " +
            "AND  (:checkOutDate >= ar.checkInDate AND :checkInDate <= ar.checkOutDate)"+
            "AND ar.status not in :excludedList"
    )
    Integer getReservedQuantity(@Param("accommodationId") Long accommodationId,
                                @Param("checkInDate") LocalDate checkInDate,
                                @Param("checkOutDate") LocalDate checkOutDate,
                                @Param("excludedList") List<ReservationStatus> excludedList

    );
    @Modifying
    @Transactional
    @Query("DELETE FROM AccommodationReservationDetail d where d.accommodationReservation.accommodationReservationId = :reservationId")
    void deleteAccommodationReservationDetailsByReservationId(@Param("reservationId")  Long reservationId);
}
