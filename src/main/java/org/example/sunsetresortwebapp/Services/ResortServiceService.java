package org.example.sunsetresortwebapp.Services;

import org.example.sunsetresortwebapp.Models.ResortService;
import org.example.sunsetresortwebapp.Repository.ServiceRepository;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResortServiceService {
    private final ServiceRepository serviceRepository;
    @Autowired
    public ResortServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }
    public List<ResortService> findServices() {
            return serviceRepository.findAll();
    }
    public ResortService findResortServiceById(Long id) {
        return serviceRepository.findByServiceId(id);
    }
}
