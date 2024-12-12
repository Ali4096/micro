package com.seeder.contract_service.exceptions;

public class ContractNotFoundException extends RuntimeException{
    public ContractNotFoundException(String message){
        super(message);
    }
}
