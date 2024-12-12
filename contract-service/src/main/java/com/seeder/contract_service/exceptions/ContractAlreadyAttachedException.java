package com.seeder.contract_service.exceptions;

public class ContractAlreadyAttachedException extends RuntimeException{
    public ContractAlreadyAttachedException(String message){
        super(message);
    }
}
