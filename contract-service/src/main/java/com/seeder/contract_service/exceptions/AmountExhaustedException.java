package com.seeder.contract_service.exceptions;

public class AmountExhaustedException extends RuntimeException{
    public AmountExhaustedException(String message){
        super(message);
    }
}
