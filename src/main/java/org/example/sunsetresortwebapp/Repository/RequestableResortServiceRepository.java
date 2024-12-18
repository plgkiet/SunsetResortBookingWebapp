package org.example.sunsetresortwebapp.Repository;

import org.example.sunsetresortwebapp.Models.RequestableResortService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestableResortServiceRepository extends JpaRepository<RequestableResortService, Long> {

}
