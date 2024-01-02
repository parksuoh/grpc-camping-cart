package com.example.cart.dtos;

public record GetCartItemsDto(
        Long cartItemId,
        Integer quantity,
        Long productId,
        Long price,
        Long productFirstOptionId,
        Long productFirstAddPrice,
        Long productSecondOptionId,
        Long productSecondAddPrice
){}

