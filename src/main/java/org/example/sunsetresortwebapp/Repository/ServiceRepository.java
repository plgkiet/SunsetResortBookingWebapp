package org.example.sunsetresortwebapp.Repository;

import org.example.sunsetresortwebapp.Models.ResortService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ResortService, Long> {
    ResortService findByServiceId(Long id);
}
