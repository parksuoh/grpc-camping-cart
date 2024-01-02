package com.example.cart.exceptions;

public class NameNotExist extends RuntimeException {

    public NameNotExist() {
        super("아이디가 존재하지 않습니다.");
    }
}
