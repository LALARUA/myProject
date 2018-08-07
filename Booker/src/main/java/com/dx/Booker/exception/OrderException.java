package com.dx.Booker.exception;

public class OrderException extends RuntimeException {
    public OrderException(String errorMessage){
        super(errorMessage);
    }
}
