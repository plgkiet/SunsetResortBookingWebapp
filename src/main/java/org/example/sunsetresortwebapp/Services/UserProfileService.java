package org.example.sunsetresortwebapp.Services;

import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Models.UserProfile;
import org.example.sunsetresortwebapp.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    private final ProfileRepository profileRepository;
    @Autowired
    public UserProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public UserProfile findUserProfileByUserId(Long userId){
         return profileRepository.findUserProfileByUserId(userId);
    }
    public UserProfile findUserProfileById(Long id){
        return profileRepository.findById(id).orElse(null);
    }
    public UserProfile updateUserProfile(String fullname, String phoneNumber, String address, User user){
            UserProfile currentUserProfile = profileRepository.findUserProfileByUserId(user.getId());
            if(currentUserProfile != null){

                if (fullname != null &&  !fullname.equalsIgnoreCase("")){
                    currentUserProfile.setFullname(fullname);
                }
                if(phoneNumber != null &&  !phoneNumber.equalsIgnoreCase("")){
                    currentUserProfile.setPhoneNumber(phoneNumber);
                }
                if(address != null &&  !address.equalsIgnoreCase("")){
                    currentUserProfile.setAddress(address);
                }
            }else{
               currentUserProfile= new UserProfile();
               currentUserProfile.setFullname(fullname);
               currentUserProfile.setPhoneNumber(phoneNumber);
               currentUserProfile.setAddress(address);
               currentUserProfile.setUser(user);
            }
            return profileRepository.save(currentUserProfile);
    }

}
