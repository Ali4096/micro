package com.seeder.contract_service.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Integer statusCode;
    private String message;
    private Map<String,String> fieldErrors = new HashMap<>();

    public ErrorResponse(Integer statusCode,String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
