package com.seeder.user_service.exceptions;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
