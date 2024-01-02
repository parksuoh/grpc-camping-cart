package com.example.cart.dtos;

public record CartItemsResponseDto(
        Long cartItemId,
        Integer quantity,
        Long productId,
        String name,
        Long price,
        Long productFirstOptionId,
        String productFirstOptionName,
        Long firstAddPrice,
        Long productSecondOptionId,
        String productSecondOptionName,
        Long productSecondPrice,
        Long itemUnitPrice,
        Long itemTotalPrice
){}
