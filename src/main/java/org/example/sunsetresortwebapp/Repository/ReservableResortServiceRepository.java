package org.example.sunsetresortwebapp.Repository;

import org.example.sunsetresortwebapp.Models.ReservableResortService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservableResortServiceRepository extends JpaRepository<ReservableResortService, Long> {
}
