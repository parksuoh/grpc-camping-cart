package com.example.cart.exceptions;

public class CartItemNotExist extends RuntimeException {

    public CartItemNotExist() {
        super("장바구니 아이템이 존재하지 않습니다.");
    }
}
