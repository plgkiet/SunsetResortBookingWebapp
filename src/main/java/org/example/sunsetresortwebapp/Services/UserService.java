package org.example.sunsetresortwebapp.Services;
import org.example.sunsetresortwebapp.Models.*;
import org.example.sunsetresortwebapp.Repository.UserRepository;
import org.example.sunsetresortwebapp.Utils.StatusComparator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserById(Long id) {
        return   userRepository.findUserById(id);
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public void deleleteUserById(long id) {

            userRepository.deleteById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user, long id) {
        User currentUser = userRepository.findUserById(id);
        if(user.getEmail() != null &&  !user.getEmail().equalsIgnoreCase("")){
            currentUser.setEmail(user.getEmail());
        }
        if(user.getPassword() != null &&  !user.getPassword().equalsIgnoreCase("")){
            currentUser.setPassword(user.getPassword());
        }
        userRepository.save(currentUser);
        return currentUser;
    }
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
    public CheckUserResponse checkUser(String email , String password){
        User user = userRepository.findUserByEmail(email);
        CheckUserResponse response = new CheckUserResponse();
        if(user == null){
            response.setMessage("User not found");
            response.setSuccess(false);
        }else{
            String hashedPassword = BCrypt.hashpw(password, user.getSalt());
            System.out.println(hashedPassword);
            System.out.println(user.getPassword());
            if(!hashedPassword.equals(user.getPassword())){
                response.setMessage("Wrong password");
                response.setSuccess(false);
            }else{
                response.setMessage("Successfully logged in");
                response.setSuccess(true);
            }
        }
        return response;
    }
    public CheckUserResponse registerUser( String email,  String password,   String confirmPassword){
        CheckUserResponse response = new CheckUserResponse();
        if(!password.equals(confirmPassword)){
            response.setMessage("Passwords do not match");
            response.setSuccess(false);
        }else {
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                user = new User();
                String salt = BCrypt.gensalt();
                user.setSalt(salt);
                user.setEmail(email);
                user.setPassword(BCrypt.hashpw(password, salt));
                userRepository.save(user);
                response.setMessage("User successfully registered");
                response.setSuccess(true);

            } else {
                response.setMessage("Email already existed");
                response.setSuccess(false);
            }
        }
        return response;
    }
    public CheckUserResponse changePassword(String inputCurrentPassword, String newPassword, String confirmPassword, User user){
        CheckUserResponse response = new CheckUserResponse();
        String inputCurrentHashedPassword = BCrypt.hashpw(inputCurrentPassword, user.getSalt());
        if(inputCurrentHashedPassword.equals(user.getPassword())){
            if(confirmPassword.equals(newPassword)){
                 String salt = BCrypt.gensalt();
                 user.setSalt(salt);
                 user.setPassword(BCrypt.hashpw(newPassword,salt));
                 response.setSuccess(true);
                 response.setMessage("Password changed successfully");
                 userRepository.save(user);
            }else{
                response.setSuccess(false);
                response.setMessage("Passwords do not match");
            }
        }else{
            response.setSuccess(false);
            response.setMessage("Wrong password");
        }
        return response;
    }
    public List<AccommodationReservation> getAllSortedAccommodationReservations(User user){
        List<AccommodationReservation> accommodationReservations = user.getAccommodationReservations();
        Comparator<AccommodationReservation> statusComparator = new StatusComparator().getStatusComparatorForAccommodationReservation();
        accommodationReservations.sort(statusComparator);
        return  accommodationReservations;
    }
    public List<ReservableServiceReservation> getAllSortedReservableServiceReservation(User user){
        List<ReservableServiceReservation> reservableServiceReservations = user.getReservableServiceReservations();
        Comparator<ReservableServiceReservation> statusComparator = new StatusComparator().getStatusComparatorForReservableServiceReservation();
        reservableServiceReservations.sort(statusComparator);
        return reservableServiceReservations;
    }
    public List<RequestableServiceRequest> getAllSortedRequestableServiceRequests(User user){
        List<RequestableServiceRequest> requestableServiceRequests = user.getRequestableServiceRequests();
        Comparator<RequestableServiceRequest> statusComparator = new StatusComparator().getStatusComparatorForRequestableServiceRequest();
        requestableServiceRequests.sort(statusComparator);
        return requestableServiceRequests;
    }
    public List<User> getAllSortedUsers(){
        List<User> users  =  userRepository.findAll();
        Comparator<User> userStatusComparator = Comparator.comparingInt((user) -> {
            switch(user.getStatus()) {
                case ACTIVATED:
                    return 1;
                case BANNED:
                    return 2;
                case DEACTIVATED:return 3;
                default:
                    return 4;
            }
        });

        users.sort(userStatusComparator);
        return users;
    }
    public List<User> getAllSortedUsers(List<User> users){
        Comparator<User> userStatusComparator = Comparator.comparingInt((user) -> {
            switch(user.getStatus()) {
                case ACTIVATED:
                    return 1;
                case BANNED:
                    return 2;
                case DEACTIVATED:return 3;
                default:
                    return 4;
            }
        });

        users.sort(userStatusComparator);
        return users;
    }
}