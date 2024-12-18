package org.example.sunsetresortwebapp.Services;

import jakarta.transaction.Transactional;
import org.example.sunsetresortwebapp.DTO.RequestableServiceDTO;
import org.example.sunsetresortwebapp.Models.RequestableResortService;
import org.example.sunsetresortwebapp.Models.RequestableServiceRequest;
import org.example.sunsetresortwebapp.Repository.RequestableResortServiceRepository;
import org.example.sunsetresortwebapp.Repository.RequestableServiceRequestRepository;
import org.example.sunsetresortwebapp.Utils.StatusComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RequestableResortServiceService {
    private final RequestableResortServiceRepository requestableResortServiceRepository;
    private final RequestableServiceRequestRepository requestableServiceRequestRepository;

    @Autowired
    public RequestableResortServiceService(RequestableResortServiceRepository requestableResortServiceRepository, RequestableServiceRequestRepository requestableServiceRequestRepository) {
        this.requestableResortServiceRepository = requestableResortServiceRepository;
        this.requestableServiceRequestRepository = requestableServiceRequestRepository;
    }
    public RequestableResortService createRequestableResortService(RequestableServiceDTO requestableServiceDTO) {
        RequestableResortService requestableResortService = new RequestableResortService();
        requestableResortService.setRequestDescription(requestableServiceDTO.description());
        requestableResortService.setRequestName(requestableServiceDTO.name());
        requestableResortService.setPrice((double) requestableServiceDTO.price());
        return requestableResortServiceRepository.save(requestableResortService);
    }
    public List<RequestableResortService> getAllRequestableResortServices() {
        return requestableResortServiceRepository.findAll();
    }
    public RequestableResortService getRequestableResortServiceById(Long id) {
        return requestableResortServiceRepository.findById(id).orElse(null);
    }
    public void deleteRequestableResortServiceById(Long id) {
        requestableResortServiceRepository.deleteById(id);
    }
    @Transactional
    public void updateRequestableResortServiceById(Long id, RequestableResortService requestableResortService) {
        requestableResortServiceRepository.save(requestableResortService);
    }


}
