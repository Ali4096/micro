package com.seeder.cashkick_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
public class CashkickDTO {

    @NotBlank(message = "cash kick name is mandatory")
    private String cashkickName;

    private String cashkickStatus; // This will be mapped from Status Enum (e.g., "PENDING", "APPROVED")

    private String maturityDate; // This will be converted from LocalDate to String

    private Double totalReceived;

    @NotNull(message = "termLength can not be null")
    @Min(value = 1, message = "termLength must be a positive number")
    private Integer termLength;


    @NotNull(message = "user cannot be null")
    private Long user_id;

    // Instead of mapping full contracts, just store the contract IDs (as a list of integers)
    @NotNull(message = "contracts cannot be null")
    private List<Long> contractIds;  // List of contract IDs, no need to map full ContractDTOs


//
//    // Utility method to convert Enum to String
//    public void setCashkickStatusFromEnum(com.zemoso.cashkick.cashkickApp.entities.CashKick.Status status) {
//        if (status != null) {
//            this.cashkickStatus = status.name(); // Store as "PENDING", "APPROVED", etc.
//        }
//    }
//
//    // Utility method to convert String to Enum
//    public com.zemoso.cashkick.cashkickApp.entities.CashKick.Status getCashkickStatusAsEnum() {
//        if (this.cashkickStatus != null) {
//            return com.zemoso.cashkick.cashkickApp.entities.CashKick.Status.valueOf(this.cashkickStatus); // Convert back to Enum
//        }
//        return null;
//    }
//
//    // Utility method to convert LocalDate to String (for the DTO)
//    public void setMaturityDateFromEntity(LocalDate maturityDate) {
//        if (maturityDate != null) {
//            this.maturityDate = String.valueOf(LocalDate.parse(maturityDate.format(DateTimeFormatter.ISO_LOCAL_DATE))); // Format as ISO string (e.g., "2024-11-29")
//        }
//    }
//
//    // Utility method to convert String back to LocalDate (when setting from DTO to Entity)
//    public LocalDate getMaturityDateAsEntity() {
//        return this.maturityDate == null ? null : LocalDate.parse((CharSequence) this.maturityDate, DateTimeFormatter.ISO_LOCAL_DATE);
//    }
}
