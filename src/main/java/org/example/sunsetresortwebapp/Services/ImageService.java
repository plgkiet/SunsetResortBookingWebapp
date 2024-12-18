package org.example.sunsetresortwebapp.Services;

import org.example.sunsetresortwebapp.Models.*;
import org.example.sunsetresortwebapp.Repository.AccommodationRepository;
import org.example.sunsetresortwebapp.Repository.ImageRepository;
import org.example.sunsetresortwebapp.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final AccommodationService accommodationService;
    private final ResortServiceService resortServiceService;

    @Autowired
    public ImageService(ImageRepository imageRepository, AccommodationService accommodationService, ResortServiceService resortServiceService) {
        this.imageRepository = imageRepository;
        this.accommodationService = accommodationService;
        this.resortServiceService = resortServiceService;
    }






    public Image  createImage(String iamgeUrl, Long accommodationId, Long resortServiceId, String type){
        Accommodation accommodation =null;
        ResortService resortService =null;
        Image image = null;
        if(type.equals("Accommodation")){
            image = new AccommodationImage();
            accommodation = accommodationService.getAccommodationById(accommodationId);
            image.setAccommodation(accommodation);

        }else{
            image = new ServiceImage();
            resortService = resortServiceService.findResortServiceById(resortServiceId);
            image.setResortService(resortService);
        }
        image.setImageUrl(iamgeUrl);
        return imageRepository.save(image);

    }

    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }
   public Image getImageById(Long id){
        return imageRepository.findByImageId(id);
   }
}
