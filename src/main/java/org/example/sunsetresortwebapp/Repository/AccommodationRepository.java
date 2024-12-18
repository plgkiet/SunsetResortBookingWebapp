package org.example.sunsetresortwebapp.Repository;

import org.example.sunsetresortwebapp.Models.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Accommodation findByAccommodationId(long id);
    @Query("select  a from Accommodation  a left join fetch  a.images")
    List<Accommodation> findAccommodationWithAllImages();
}
