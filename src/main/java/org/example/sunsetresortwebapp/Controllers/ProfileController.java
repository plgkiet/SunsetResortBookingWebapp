package org.example.sunsetresortwebapp.Controllers;

import jakarta.servlet.http.HttpSession;
import org.example.sunsetresortwebapp.Models.AccommodationReservation;
import org.example.sunsetresortwebapp.Models.CheckUserResponse;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Models.UserProfile;
import org.example.sunsetresortwebapp.Services.AccommodationReservationService;
import org.example.sunsetresortwebapp.Services.ReservableServiceReservationService;
import org.example.sunsetresortwebapp.Services.UserProfileService;
import org.example.sunsetresortwebapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class ProfileController {
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Autowired
    public ProfileController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public String getProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if(user != null){
            UserProfile userProfile = userProfileService.findUserProfileByUserId(user.getId());
            user = userService.findUserById(user.getId());
            session.setAttribute("loggedInUsers", user);
            session.setAttribute("userProfile", userProfile);
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("user", user);
            model.addAttribute("reservationHistory", userService.getAllSortedAccommodationReservations(user));
            model.addAttribute("reservableReservationHistory",userService.getAllSortedReservableServiceReservation(user));
            model.addAttribute("requests",userService.getAllSortedRequestableServiceRequests(user));
            System.out.println(user.getAccommodationReservations());
            return "profile";
        }else{
            return "redirect:/signin";
        }
    }
    @PostMapping("updateprofile")
    public String updateProfile(@RequestParam String fullname, @RequestParam String email, @RequestParam("phonenumber") String phoneNumber, @RequestParam String address, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
            User user = (User) session.getAttribute("loggedInUser");
           if(user == null){
               return "redirect:/signin";
           }else{
               user.setEmail(email);
              User newUser = userService.updateUser(user,user.getId());
              UserProfile newUserProfile = userProfileService.updateUserProfile(fullname,phoneNumber, address,newUser);
               redirectAttributes.addFlashAttribute("userProfile", newUserProfile );
               redirectAttributes.addFlashAttribute("user", newUser);
               redirectAttributes.addFlashAttribute("profilemessage", "Successfully updated profile");
           }
            return "redirect:/profile";
    }
    @PostMapping("/changepassword")
    public String updateChangePassword(@RequestParam("currentpassword") String inputCurrentPassword, @RequestParam("newpassword") String password, @RequestParam("confirmpassword") String confirmPassword, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if(user != null){
            CheckUserResponse response = userService.changePassword(inputCurrentPassword,password,confirmPassword,user);
            redirectAttributes.addFlashAttribute("response", response);
            session.setAttribute("loggedInUsers", userService.findUserById(user.getId()));
            return "redirect:/profile?section=password";
        }else{
            return "redirect:/signin";
        }
    }
}
