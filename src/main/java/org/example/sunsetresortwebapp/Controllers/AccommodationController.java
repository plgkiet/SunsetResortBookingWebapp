package org.example.sunsetresortwebapp.Controllers;

import jakarta.servlet.http.HttpSession;
import org.example.sunsetresortwebapp.DTO.AccommodationSearchingDTO;
import org.example.sunsetresortwebapp.DTO.RequestReservationStatusDTO;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Services.AccommodationReservationDetailService;
import org.example.sunsetresortwebapp.Services.AccommodationReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.example.sunsetresortwebapp.Models.Accommodation;
import org.example.sunsetresortwebapp.Services.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AccommodationController {
    private final AccommodationService accommodationService;
    private final AccommodationReservationDetailService accommodationReservationDetailService;
    private final AccommodationReservationService accommodationReservationService;
    @Autowired
    public AccommodationController(AccommodationService accommodationService, AccommodationReservationDetailService accommodationReservationDetailService, AccommodationReservationService accommodationReservationService) {
        this.accommodationService = accommodationService;
        this.accommodationReservationDetailService = accommodationReservationDetailService;
        this.accommodationReservationService = accommodationReservationService;
    }

    @GetMapping("/accommodations")
    public String getAllAccommodations(Model model) {
       if(!model.containsAttribute("accommodations")) {
           List<Accommodation> accommodations = accommodationService.getAllAccommodations();
           model.addAttribute("accommodations", accommodations);
       }
        return "accommodation";

    }
    @PostMapping("/accommodations")
    public String createAccommodation( @RequestBody  Accommodation accommodation , Model model ) {
           Accommodation accommodation1 =  accommodationService.createAccommodation(accommodation);
           model.addAttribute("accommodation", accommodation1);
           return "accommodation";
    }
    @GetMapping("/accommodations/detail-booking/{accommodation-id}")
    public String getAccommodationDetails(@PathVariable("accommodation-id") Long accommodationId, Model model) {
        Accommodation accommodation = accommodationService.getAccommodationById(accommodationId);
        model.addAttribute("accommodation", accommodation);
        return "accommodationdetail";
    }

    @GetMapping("/accommodations/search/{accommodation-id}/{check-in-date}/{check-out-date}")
    public String searchAccommodation(@PathVariable("accommodation-id") Long accommodationId,@PathVariable("check-in-date") String checkInDate, @PathVariable("check-out-date") String checkOutDate, Model model) {
            Accommodation accommodation = accommodationService.getAccommodationById(accommodationId);
            model.addAttribute("accommodation", accommodation);
            Map<Long, Integer> availabilityMap = new HashMap<>();
            int availability = accommodationReservationDetailService.getReservedAvailability(accommodationId, LocalDate.parse(checkInDate),  LocalDate.parse(checkOutDate));
            availabilityMap.put(accommodationId, availability);
             model.addAttribute("availabilityMap", availabilityMap);
             return "accommodationfiltering";
    }

    @PostMapping("/accommodations/search-accommodation")
    public String searchAccommodation(@ModelAttribute AccommodationSearchingDTO accommodationSearchingDTO, Model model, RedirectAttributes redirectAttributes) {
        List<Accommodation> filteredAccommodations = accommodationService.filterAccommodationWithNumberOfGuests(accommodationSearchingDTO.numberOfGuests());
        redirectAttributes.addFlashAttribute("checkInDate", accommodationSearchingDTO.checkInDate());
        redirectAttributes.addFlashAttribute("checkOutDate", accommodationSearchingDTO.checkOutDate());
        redirectAttributes.addFlashAttribute("numberOfGuests", accommodationSearchingDTO.numberOfGuests());
        if (filteredAccommodations.size() > 0) {
            Map<Long,Integer>  availabilityMap = accommodationReservationDetailService.getReservedAvailabilityForAllAccommodations(filteredAccommodations, LocalDate.parse(formatDate(accommodationSearchingDTO.checkInDate())), LocalDate.parse(formatDate(accommodationSearchingDTO.checkOutDate())));
            redirectAttributes.addFlashAttribute("accommodations", filteredAccommodations);
            redirectAttributes.addFlashAttribute("availabilityMap", availabilityMap);
        } else {
            redirectAttributes.addFlashAttribute("error", "No Accommodation has that number of guests, please try another!");
        }
        return "redirect:/accommodations";
    }
    public String formatDate(String date){
        String [] dates = date.split("/");
        return dates[2] + "-" + dates[0] + "-" + dates[1];
    }

    @PostMapping("/accommodations/cancel-reservations")
    public ResponseEntity<Map<String,Object>> cancelAccommodationReservations(@RequestBody RequestReservationStatusDTO requestReservationStatusDTO, HttpSession session){
        Map<String,Object> response = new HashMap<>();
        if(session.getAttribute("loggedInUser") == null){
                response.put("status", "error");
                response.put("redirectUrl", "/signin");
        }else{
            User user  = (User) session.getAttribute("loggedInUser");
            accommodationReservationService.updateAccommodationReservationStatus(requestReservationStatusDTO.bookingID(), ReservationStatus.CANCELED);
            response.put("status", "success");
            response.put("redirectUrl", "/profile?section=booking");
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/accommodations/update-reservation-status")
    public ResponseEntity<Map<String,Object>> updateAccommodationReservationStatus(@RequestBody RequestReservationStatusDTO requestReservationStatusDTO, HttpSession session){
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
            accommodationReservationService.updateAccommodationReservationStatus(requestReservationStatusDTO.bookingID(),status);
            response.put("status", "success");
            response.put("redirectUrl", "/admindashboard");
        }
        return ResponseEntity.ok(response);
    }


//    @PostMapping("/accommodations/makereservation")
//    public String makeAccommodationReservation(@RequestBody AccommodationReservationDTO accommodationReservationDTO, Model model) {
//        model.addAttribute("message", "Succesfully make a reservation");
//        accommodationReservationService.makeReservation(accommodationReservationDTO);
//        return "accommodationfiltering";
//    }




}
