package com.example.cart.exceptions;

public class CartAndCartItemNotMatch extends RuntimeException {

    public CartAndCartItemNotMatch() {
        super("장바구니와 장바구니아이템의 관계가 틀립니다.");
    }
}
