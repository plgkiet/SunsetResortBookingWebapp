package org.example.sunsetresortwebapp.DTO;

public record RequestableServiceDTO(
        String name, String imageUrl, String description, int price
) {
}
