package com.seeder.contract_service.exceptions;

public class ContractAlreadyPendingException extends RuntimeException{
    public ContractAlreadyPendingException(String message){
        super(message);
    }
}
