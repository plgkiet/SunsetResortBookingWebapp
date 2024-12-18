package org.example.sunsetresortwebapp.Controllers;

import jakarta.servlet.http.HttpSession;
import org.example.sunsetresortwebapp.DTO.AccommodationReservationDTO;
import org.example.sunsetresortwebapp.Models.ReservableResortService;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Services.AccommodationReservationService;
import org.example.sunsetresortwebapp.Services.ReservableResortServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReservationController {

    private final AccommodationReservationService accommodationReservationService;
    private final ReservableResortServiceService reservableResortServiceService;
    @Autowired
    public ReservationController(AccommodationReservationService accommodationReservationService, ReservableResortServiceService reservableResortServiceService) {
        this.accommodationReservationService = accommodationReservationService;
        this.reservableResortServiceService = reservableResortServiceService;
    }
    //    @GetMapping("/reservations")
//    public String getReservations(Model model, HttpSession session) {
//        if(session.getAttribute("loggedInUser") == null){
//            return "redirect:/signin";
//        }else{
//            User user = (User)session.getAttribute("loggedInUser");
//            List<AccommodationReservation> accommodationReservations = accommodationReservationService.getAllRervationsByUserId(user);
//            model.addAttribute("reservations", accommodationReservations);
//        }
//        return  "redirect:/profile?section=booking";
//    }

    @GetMapping("/reservations/get-reservations")
    public ResponseEntity<Map<String,Object>> getReservation(  HttpSession session) {
        Map<String,Object> response = new HashMap<>();
       if(session.getAttribute("loggedInUser") == null){
           response.put("status","error");
           response.put("redirectURL","/signin");
       }else{
           response.put("status","success");
           response.put("redirectURL" , "/payment-accommodations");
       }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment-accommodations")
    public String payment(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", user);
        return "paymentforaccommodation";
    }


    @PostMapping("/reservations/make-reservations")
    public ResponseEntity<Map<String,Object>> makeReservation(@RequestBody AccommodationReservationDTO accommodationReservationDTO, HttpSession session, Model model) {

        Map<String,Object> response = new HashMap<>();
      if(session.getAttribute("loggedInUser") == null){
          response.put("status" , "error");
          response.put("redirectURL" , "/signin");
      }else{
          User user = (User) session.getAttribute("loggedInUser");
          accommodationReservationService.makeReservations(user, accommodationReservationDTO);
          response.put("status","success");
          response.put("redirectURL", "/payment-accommodations");
      }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/reservations/reservable-service-reserve")
    public ResponseEntity<Map<String,Object>> createReservableServices(@RequestBody ReservableResortService reservableResortService){
        this.reservableResortServiceService.createReservableResortService(reservableResortService);
        Map<String,Object> response = new HashMap<>();
        response.put("status","success");
        return ResponseEntity.ok(response);
    }
}
