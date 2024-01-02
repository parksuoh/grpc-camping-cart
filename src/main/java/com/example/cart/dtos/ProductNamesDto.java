package com.example.cart.dtos;

public record ProductNamesDto(
        String productName,
        Long productPrice,
        String firstName,
        Long firstOptionPrice,
        String secondName,
        Long secondOptionPrice
) {
}
