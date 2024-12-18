package org.example.sunsetresortwebapp.Repository;

import jakarta.transaction.Transactional;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.AccommodationReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationReservationRepository extends JpaRepository<AccommodationReservation, Long> {
    List<AccommodationReservation>  findAccommodationReservationsByUserId(@Param("userId") Long userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM AccommodationReservation  ar where ar.user.id = :userId")
    void deleteAccommodationReservationsByUserId(@Param("userId") Long userId);
}
