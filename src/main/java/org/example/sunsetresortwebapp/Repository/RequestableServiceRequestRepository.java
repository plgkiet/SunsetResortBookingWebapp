package org.example.sunsetresortwebapp.Repository;

import jakarta.transaction.Transactional;
import org.example.sunsetresortwebapp.Models.RequestableServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestableServiceRequestRepository extends JpaRepository<RequestableServiceRequest, Long> {
   @Modifying
   @Transactional
    @Query("DELETE  FROM RequestableServiceRequest  rsr where rsr.user.id = :userId")
    void deleteRequestableServiceRequestsByUserId(Long userId);
}
