package com.example.cart.dtos;

public class ProductOptionNotMatch extends RuntimeException {

    public ProductOptionNotMatch() {
        super("상품옵션과 상품이 매칭되지 않습니다.");
    }
}
