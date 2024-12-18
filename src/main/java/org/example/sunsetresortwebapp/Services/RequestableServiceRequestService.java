package org.example.sunsetresortwebapp.Services;

import jakarta.transaction.Transactional;
import org.example.sunsetresortwebapp.DTO.RequestableResortServiceRequestDTO;
import org.example.sunsetresortwebapp.DTO.RequestableResortServiceResponseDTO;
import org.example.sunsetresortwebapp.Enum.ReservationStatus;
import org.example.sunsetresortwebapp.Models.RequestableResortService;
import org.example.sunsetresortwebapp.Models.RequestableServiceRequest;
import org.example.sunsetresortwebapp.Models.User;
import org.example.sunsetresortwebapp.Repository.RequestableResortServiceRepository;
import org.example.sunsetresortwebapp.Repository.RequestableServiceRequestRepository;
import org.example.sunsetresortwebapp.Utils.StatusComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RequestableServiceRequestService {
    private final RequestableServiceRequestRepository requestableServiceRequestRepository;
    @Autowired
    public RequestableServiceRequestService(RequestableServiceRequestRepository requestableServiceRequestRepository) {
        this.requestableServiceRequestRepository = requestableServiceRequestRepository;
    }

    public RequestableServiceRequest getRequestableServiceRequest(Long requestId) {
        return requestableServiceRequestRepository.findById(requestId).orElse(null);
    }
    public List<RequestableServiceRequest> getAllRequestableServiceRequests() {
        return requestableServiceRequestRepository.findAll();
    }
    public void cancelRequestableServiceRequest(Long requestId) {
        RequestableServiceRequest request = requestableServiceRequestRepository.findById(requestId).orElse(null);
        if(request != null){
            request.setStatus(ReservationStatus.CANCELED);
        }
    }
    public RequestableResortServiceResponseDTO makeRequest(RequestableResortServiceRequestDTO requestableResortServiceRequestDTO, User user, RequestableResortService requestableResortService){
        RequestableResortServiceResponseDTO requestableResortServiceResponseDTO = new RequestableResortServiceResponseDTO();
        RequestableServiceRequest requestableServiceRequest = new RequestableServiceRequest();
        requestableServiceRequest.setRequestDate(requestableResortServiceRequestDTO.requestDate());
        requestableServiceRequest.setRequestTime(requestableResortServiceRequestDTO.requestTime());
        requestableServiceRequest.setUser(user);
        requestableServiceRequest.setStatus(ReservationStatus.PENDING);
        requestableServiceRequest.setRoomName(requestableResortServiceRequestDTO.roomName());
        requestableServiceRequest.setRequestableResortService(requestableResortService);
        requestableServiceRequestRepository.save(requestableServiceRequest);
        requestableResortServiceResponseDTO.setSuccess(true);
        requestableResortServiceResponseDTO.setMessage("Successfully make a request");
        return requestableResortServiceResponseDTO;
    }
    public void updateRequestableServiceRequestById(Long requestableServiceRequestId){
        RequestableServiceRequest request = requestableServiceRequestRepository.findById(requestableServiceRequestId).get();
        request.setStatus(ReservationStatus.CANCELED);
        requestableServiceRequestRepository.save(request);
    }
    public List<RequestableServiceRequest>  getAllSortedRequestableServiceRequest(){
        List<RequestableServiceRequest> requestableServiceRequests = requestableServiceRequestRepository.findAll();
        Comparator<RequestableServiceRequest> statusComparator = new StatusComparator().getStatusComparatorForRequestableServiceRequest();
        requestableServiceRequests.sort(statusComparator);
        return requestableServiceRequests;
    }
    public void updateRequestableServiceRequestById(Long requestableServiceRequestId, ReservationStatus status){
        RequestableServiceRequest request = requestableServiceRequestRepository.findById(requestableServiceRequestId).get();
        request.setStatus(status);
        requestableServiceRequestRepository.save(request);
    }

    @Transactional
    public void deleteRequestableServiceRequestByUserId(Long userId){
        requestableServiceRequestRepository.deleteRequestableServiceRequestsByUserId(userId);
    }


}
