package org.example.sunsetresortwebapp.Services;

import org.example.sunsetresortwebapp.Models.ReservableResortService;
import org.example.sunsetresortwebapp.Repository.ReservableResortServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservableResortServiceService {
    private final ReservableResortServiceRepository reservableResortServiceRepository;
    @Autowired
    public ReservableResortServiceService(ReservableResortServiceRepository reservableResortServiceRepository) {
        this.reservableResortServiceRepository = reservableResortServiceRepository;
    }
    public void createReservableResortService(ReservableResortService reservableResortService) {
        reservableResortServiceRepository.save(reservableResortService);
    }
    public List<ReservableResortService> getAllReservableResortServices() {
        return reservableResortServiceRepository.findAll();
    }
    public ReservableResortService getReservableResortServiceById(Long id) {
        return reservableResortServiceRepository.findById(id).orElse(null);
    }
    public void updateReservableResortService(ReservableResortService reservableResortService) {
        reservableResortServiceRepository.save(reservableResortService);
    }
}
