package org.example.sunsetresortwebapp.Repository;

import org.example.sunsetresortwebapp.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
        Image findByImageId(long id);
}
