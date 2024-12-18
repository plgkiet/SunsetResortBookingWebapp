package org.example.sunsetresortwebapp.Controllers;


import jakarta.servlet.http.HttpSession;
import org.example.sunsetresortwebapp.DTO.RequestReservationStatusDTO;
import org.example.sunsetresortwebapp.DTO.ReservableServiceRequestDTO;
import org.example.sunsetresortwebapp.DTO.ReservableServiceResponseDTO;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.ReservableResortService;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Repository.ReservableServiceReservationRepository;
import org.example.sunsetresortwebapp.Services.ReservableResortServiceService;
import org.example.sunsetresortwebapp.Services.ReservableServiceReservationService;
import org.example.sunsetresortwebapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReservableServiceController {
    private final ReservableResortServiceService reservableResortServiceService;
    private final UserService userService;
    private final ReservableServiceReservationService reservableServiceReservationService;
    private final ReservableServiceReservationRepository reservableServiceReservationRepository;

    @Autowired
    public ReservableServiceController(ReservableResortServiceService reservableResortServiceService, UserService userService, ReservableServiceReservationService reservableServiceReservationService, ReservableServiceReservationRepository reservableServiceReservationRepository) {
        this.reservableResortServiceService = reservableResortServiceService;
        this.userService = userService;
        this.reservableServiceReservationService = reservableServiceReservationService;
        this.reservableServiceReservationRepository = reservableServiceReservationRepository;
    }
    @GetMapping("/reservable-services")
    public String getReservableServices(Model model) {
        List<ReservableResortService> reservableResortServices = reservableResortServiceService.getAllReservableResortServices();
        model.addAttribute("reservableServices", reservableResortServices);
        return "reserveservice";
    }


    @GetMapping("/reservable-services/reserving/{reservable-service-id}")
    public String getReservableServiceReserving(@PathVariable("reservable-service-id") Long reservableServiceId, Model model, HttpSession session) {
        if(session.getAttribute("loggedInUser") == null){
            return "redirect:/signin";
        }else{
            ReservableResortService reservableResortService = reservableResortServiceService.getReservableResortServiceById(reservableServiceId);
            model.addAttribute("service", reservableResortService);
            return "reservableservicebooking";
        }

    }
    @PostMapping("/reservable-services/reserving")
    public String makeReservableReservation(@ModelAttribute ReservableServiceRequestDTO reservableServiceRequestDTO, Model model, HttpSession session){
        if(session.getAttribute("loggedInUser") == null){
            return "redirect:/signin";
        }else{
            User user = (User) session.getAttribute("loggedInUser");
            user = userService.findUserById(user.getId());
            ReservableResortService reservableResortService = reservableResortServiceService.getReservableResortServiceById(reservableServiceRequestDTO.reservableResortServiceId());
            ReservableServiceResponseDTO reservableServiceResponseDTO = reservableServiceReservationService.createReservableServiceReservation(reservableResortService, reservableServiceRequestDTO, user);
            model.addAttribute("response", reservableServiceResponseDTO);
            model.addAttribute("service", reservableResortService);
        }
        return "reservableservicebooking";
    }
    @PostMapping("/reservable-services/cancel-reservations")
    public ResponseEntity<Map<String,Object>> cancelReservableReservation(@RequestBody RequestReservationStatusDTO requestCancelDTO){
        Map<String,Object> response = new HashMap<>();
        reservableServiceReservationService.updateReservableServiceReservationStautus(requestCancelDTO.bookingID());
        response.put("status", "success");
        response.put("redirectUrl", "/profile?section=booking");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservable-services/update-reservation-status")
    public ResponseEntity<Map<String,Object>> updateResevableReservatoinStatus(@RequestBody RequestReservationStatusDTO requestReservationStatusDTO, HttpSession session){
        Map<String,Object> response = new HashMap<>();
        if(session.getAttribute("loggedInAdmin") == null){
            response.put("status", "error");
            response.put("redirectUrl", "/signin");
        }else{
            User user  = (User) session.getAttribute("loggedInUser");
            ReservationStatus status =null;
            System.out.println(requestReservationStatusDTO);
            if(requestReservationStatusDTO.status().equals("ACCEPT")){
                status = ReservationStatus.CONFIRMED;
            }else if(requestReservationStatusDTO.status().equals("DECLINE")){
                status = ReservationStatus.REJECTED;
            }
           reservableServiceReservationService.updateReservableServiceReservationStatus(requestReservationStatusDTO.bookingID(),status);
            response.put("status", "success");
            response.put("redirectUrl", "/admindashboard");
        }
        return ResponseEntity.ok(response);
    }
}
