package org.example.sunsetresortwebapp.Controllers;

import org.example.sunsetresortwebapp.DTO.ImageDTO;
import org.example.sunsetresortwebapp.Models.Image;
import org.example.sunsetresortwebapp.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ImageController {
    private final ImageService imageService;
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @GetMapping("/images")
    public List<Image> getImages() {
        return imageService.getAllImages();
    }
    @PostMapping("/images/")
    public String addImage(@RequestBody ImageDTO imageDTO, Model model) {
       Image image  = imageService.createImage(imageDTO.imageUrl(),imageDTO.accommodationId(), null, "Accommodation");
        model.addAttribute("image", image);
        return "accommodation";
    }
    @PostMapping("/images/reservable-services")
    public ResponseEntity<Map<String,Object>> addImageForReserableService(@RequestBody ImageDTO imageDTO, Model model) {
        Image image  = imageService.createImage(imageDTO.imageUrl(),null, imageDTO.reservableServiceId(), "ServiceImage");
        model.addAttribute("image", image);
        Map<String,Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/images/requestable-services")
    public ResponseEntity<Map<String,Object>> addImageForRequestableService(@RequestBody ImageDTO imageDTO, Model model) {
        Image image  = imageService.createImage(imageDTO.imageUrl(),null, imageDTO.reservableServiceId(), "ServiceImage");
        model.addAttribute("image", image);
        Map<String,Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
