package com.seeder.cashkick_service.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

}

