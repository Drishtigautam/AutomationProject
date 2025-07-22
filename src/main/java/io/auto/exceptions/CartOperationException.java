package io.auto.exceptions;

public class CartOperationException extends RuntimeException{

    public CartOperationException(String message){
        super(message);
    }
}
