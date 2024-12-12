package com.seeder.user_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreditDTO {

    @NotNull(message = "Allotted amount cannot be null")
    @Min(value = 0, message = "Allotted amount must be at least 0")
    private Double allotted;

    @NotNull(message = "Balance cannot be null")
    @Min(value = 0, message = "Balance must be at least 0")
    private Double balance;


}
