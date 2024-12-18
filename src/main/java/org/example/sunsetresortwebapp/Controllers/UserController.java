package org.example.sunsetresortwebapp.Controllers;

import jakarta.servlet.http.HttpSession;
import org.example.sunsetresortwebapp.Enum.UserRole;
import org.example.sunsetresortwebapp.Models.Accommodation;
import org.example.sunsetresortwebapp.Models.CheckUserResponse;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Repository.UserRepository;
import org.example.sunsetresortwebapp.Services.AccommodationReservationService;
import org.example.sunsetresortwebapp.Services.AccommodationService;
import org.example.sunsetresortwebapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
        private final UserService userService;
        private final UserRepository userRepository;
        private final AccommodationService accommodationService;

        public UserController(UserService userService, UserRepository userRepository, AccommodationService accommodationService) {
                this.userService = userService;
                this.userRepository = userRepository;
                this.accommodationService = accommodationService;
        }

        @GetMapping("/signup")
        public String register() {
            return "signup";
        }

        @GetMapping("/bookingvilla")
        public String bookingvilla() {
                return "accommodationdetail";
        }

        @GetMapping("/signin")
        public String signin() {
                return "signin";
        }

        @GetMapping("/logout/{role}")
        public String logout(@PathVariable("role") String role, HttpSession session) {
               if(role.equals("admin")){
                       session.removeAttribute("loggedInAdmin");
               }else if(role.equals("user")){
                       session.removeAttribute("loggedInUser");
               }
                return "redirect:/signin";
        }
        @GetMapping("/homepage")
        public String homepage(HttpSession session, Model model) {
                User user = (User) session.getAttribute("loggedInUser");
                if(user != null){
                        session.setAttribute("loggedInUsers", user);
                        List<Accommodation> accommodations = accommodationService.getAllAccommodations().subList(0, 3);
                        model.addAttribute("user", user);
                        model.addAttribute("accommodations", accommodations);
                        return "homepage";
                }else{
                        return "signin";
                }

        }



        @GetMapping("/showproduct")
        public String getProduct() {
                return "accommodation";
        }

        @GetMapping("/reservable-service")
        public String getReservableService() {
                return "reserveservice";
        }

        @GetMapping("/reservable-booking")
        public String getReservableBooking() {
                return "reservableservicebooking";
        }

        @GetMapping("/requestable-booking")
        public String getRequestBooking() {
                return "requestbooking";
        }

        @GetMapping("/requestable-service")
        public String getRequestService() {
                return "requestservice";
        }
       
        @GetMapping("/requestable-payment")
        public String getRequestBookingPayment() {
                return "paymentforrequest";
        }

        @GetMapping("/admin-dashboard")
        public String getAdminDashboard() {
                return "admindashboard";
        }

        @GetMapping("/general-service")
        public String getGeneralService() {
                return "generalservice";
        }

        @GetMapping("/payment")
        public String getAccommodationPayment() {
                return "paymentforaccommodation";
        }

        @GetMapping("/thankyou")
        public String getThankYouPage() {
                return "thankyou";
        }


        @PostMapping("/signin")
        public String processSignIn(@RequestParam String email, @RequestParam String password , Model model, HttpSession session){
                CheckUserResponse response = userService.checkUser(email, password);
                if(response.isSuccess()){
                        session.setMaxInactiveInterval(86400);
                        User user = userRepository.findUserByEmail(email);
                        if(user.getUserRole().equals(UserRole.ADMIN)){
                                session.setAttribute("loggedInAdmin", user);
                                model.addAttribute("loggedInAdmin", user);
                                return "redirect:/admindashboard";
                        }else{
                                session.setAttribute("loggedInUser", user);
                                model.addAttribute("loggedInUser",user);
                                return "redirect:/homepage";
                        }
                }else{
                        model.addAttribute("error", response.getMessage());
                        return "signin";
                }
        }
        @PostMapping("/signup")
        public String processSignUp(@RequestParam String email, @RequestParam String password, @RequestParam  String confirmPassword, Model model){
                CheckUserResponse response = userService.registerUser(email, password, confirmPassword);
                model.addAttribute("response", response);
                return "signup";
        }

}
