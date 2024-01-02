package com.example.cart.dtos;

public record AuthUserDto(
        String name,
        String role,
        String accessToken
) {}
