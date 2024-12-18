package com.seeder.cashkick_service.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
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

    public Integer getId() {
        return id;
    }

    public String getContractName() {
        return contractName;
    }

    public String getContractType() {
        return contractType;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public Double getStartAmount() {
        return startAmount;
    }

    public int getFee() {
        return fee;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Long> getCashkickIds() {
        return cashkickIds;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public void setStartAmount(Double startAmount) {
        this.startAmount = startAmount;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCashkickIds(List<Long> cashkickIds) {
        this.cashkickIds = cashkickIds;
    }
}
