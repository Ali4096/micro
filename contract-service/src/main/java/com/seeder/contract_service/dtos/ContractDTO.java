package com.seeder.contract_service.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ContractDTO {

    private Integer id; // Typically no validation for ID, as it's auto-generated

    @NotBlank(message = "Contract name is required.")
    @Size(max = 100, message = "Contract name cannot exceed 100 characters.")
    private String contractName;

    @NotBlank(message = "Contract type is required.")
    @Size(max = 50, message = "Contract type cannot exceed 50 characters.")
    private String contractType;

    @NotNull(message = "Contract status is required.")
    private Status status;

    @NotNull(message = "Start date is required.")
    @FutureOrPresent(message = "Start date cannot be in the past.")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    @Future(message = "End date must be in the future.")
    private LocalDate endDate;

    @NotBlank(message = "Payment frequency is required.")
    @Pattern(regexp = "^(Daily|Weekly|Monthly|Yearly)$", message = "Invalid payment frequency. Accepted values are Daily, Weekly, Monthly, or Yearly.")
    private String paymentFrequency;

    @NotNull(message = "Contract amount is required.")
    @Positive(message = "Contract amount must be a positive value.")
    private Double contractAmount;

    @NotNull(message = "Paid amount is required.")
    @PositiveOrZero(message = "Paid amount cannot be negative.")
    private Double paidAmount;

    @NotNull(message = "Outstanding amount is required.")
    @PositiveOrZero(message = "Outstanding amount cannot be negative.")
    private Double outstandingAmount;

    @PastOrPresent(message = "Last payment date cannot be in the future.")
    private LocalDate lastPaymentDate;

    @NotNull(message = "Start amount is required.")
    @Positive(message = "Start amount must be a positive value.")
    private Double startAmount;

    @PositiveOrZero(message = "Fee must be zero or positive.")
    private int fee;

    @NotNull(message = "User ID is required.")
    @Positive(message = "User ID must be a positive value.")
    private Long userId;

    private List<Long> cashkickIds; // No validation needed, as it's transient and optional.

    public enum Status {
        ACTIVE,
        INACTIVE,
        PENDING,
    }
}
