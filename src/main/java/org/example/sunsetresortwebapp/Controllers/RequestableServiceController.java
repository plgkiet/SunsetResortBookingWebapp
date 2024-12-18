package org.example.sunsetresortwebapp.Controllers;


import jakarta.servlet.http.HttpSession;
import org.example.sunsetresortwebapp.DTO.RequestReservationStatusDTO;
import org.example.sunsetresortwebapp.DTO.RequestableResortServiceRequestDTO;
import org.example.sunsetresortwebapp.DTO.RequestableResortServiceResponseDTO;
import org.example.sunsetresortwebapp.DTO.RequestableServiceDTO;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.RequestableResortService;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Services.RequestableResortServiceService;
import org.example.sunsetresortwebapp.Services.RequestableServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RequestableServiceController {
   private final RequestableResortServiceService requestableResortServiceService;
   private final RequestableServiceRequestService requestableServiceRequestService;
   @Autowired
    public RequestableServiceController(RequestableResortServiceService requestableResortServiceService, RequestableServiceRequestService requestableServiceRequestService) {
        this.requestableResortServiceService = requestableResortServiceService;
        this.requestableServiceRequestService = requestableServiceRequestService;
    }

    @GetMapping("/requestable-services")
    public String getAllRequestableServices(Model model) {
        model.addAttribute("requestableServices", requestableResortServiceService.getAllRequestableResortServices());
        return "requestservice";
    }
    @PostMapping("/requestable-serivces/requesting")
    public ResponseEntity<Map<String,Object>>  createRequestableService(@RequestBody RequestableServiceDTO requestableServiceDTO) {
       Map<String,Object> response = new HashMap<>();
       requestableResortServiceService.createRequestableResortService(requestableServiceDTO);
       response.put("status", "success");
       return ResponseEntity.ok(response);
   }
   @GetMapping("/requestable-services/requesting/{request-id}")
    public String getRequestableServiceMaking(@PathVariable("request-id") Long serviceId, Model model, HttpSession session) {
    if(session.getAttribute("loggedInUser") == null){
        return "redirect:/signin";
    }else{
            RequestableResortService requestableResortService = requestableResortServiceService.getRequestableResortServiceById(serviceId);
            model.addAttribute("requestableService", requestableResortService);
    }
    return "requestbooking";
   }
   @PostMapping("/requestable-services/request-making")
    public String makeRequestableServiceReservation(@ModelAttribute RequestableResortServiceRequestDTO requestableResortServiceRequestDTO, Model model, HttpSession session){
      if(session.getAttribute("loggedInUser") == null){
          return "redirect:/signin";
      }else{
          User user=  (User) session.getAttribute("loggedInUser");
          RequestableResortService requestableResortService =  requestableResortServiceService.getRequestableResortServiceById(requestableResortServiceRequestDTO.requestableResortServiceId());
          RequestableResortServiceResponseDTO requestableResortServiceResponseDTO = requestableServiceRequestService.makeRequest(requestableResortServiceRequestDTO,user, requestableResortService);
          model.addAttribute("requestableService", requestableResortService);
          model.addAttribute("response", requestableResortServiceResponseDTO);
          return "requestbooking";
      }
   }
   @PostMapping("/requestable-services/cancel-requests")
    public ResponseEntity<Map<String,Object>> cancelRequestablServiceRequest(@RequestBody RequestReservationStatusDTO requestReservationStatusDTO){
       Map<String,Object> response = new HashMap<>();
       requestableServiceRequestService.updateRequestableServiceRequestById(requestReservationStatusDTO.bookingID());
       response.put("status", "success");
       response.put("redirectUrl", "/profile?section=booking");
       return ResponseEntity.ok(response);
   }
    @PostMapping("/requestable-services/update-request-status")
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
            requestableServiceRequestService.updateRequestableServiceRequestById(requestReservationStatusDTO.bookingID(),status);
            response.put("status", "success");
            response.put("redirectUrl", "/admindashboard");
        }
        return ResponseEntity.ok(response);
    }
}
