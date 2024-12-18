package com.seeder.contract_service.dtos;

import com.seeder.contract_service.entities.Contract;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class CreateContractDTO {
    Integer id;

    @NotBlank(message = "Contract name is mandatory")
    private String contractName;

    private String contractType;

    @NotBlank(message = "Contract status is mandatory")
    private String contractStatus; // This will be mapped from Contract.Status Enum (e.g., "ACTIVE", "INACTIVE", "PENDING")

    @NotBlank(message = "Start date is mandatory")
    private String startDate; // This will be converted to LocalDate String

    @NotBlank(message = "End date is mandatory")
    private String endDate; // This will be converted to LocalDate String

    @NotBlank(message = "Payment frequency is mandatory")
    private String paymentFrequency;

    @NotNull(message = "Contract amount cannot be null")
    @Min(value = 0, message = "Contract amount must be at least 0")
    private Double contractAmount;

    @NotNull(message = "Paid amount cannot be null")
    @Min(value = 0, message = "Paid amount must be at least 0")
    private Double paidAmount;

    @NotNull(message = "Outstanding amount cannot be null")
    @Min(value = 0, message = "Outstanding amount must be at least 0")
    private Double outstandingAmount;

    private String lastPaymentDate; // This will be converted to LocalDate String

    @NotNull(message = "Start amount cannot be null")
    @Min(value = 0, message = "Start amount must be at least 0")
    private Double startAmount;

    @Min(value = 0, message = "Fee must be at least 0")
    private int fee;

    // Utility method to convert Enum to String
    public void setContractStatusFromEnum(Contract.Status status) {
        if (status != null) {
            this.contractStatus = status.name(); // Store as "ACTIVE", "INACTIVE", etc.
        }
    }

    // Utility method to convert String to Enum
    public Contract.Status getContractStatusAsEnum() {
        if (this.contractStatus != null) {
            return Contract.Status.valueOf(this.contractStatus); // Convert back to Enum
        }
        return null;
    }

    // Utility method to convert LocalDate to String (for the DTO)
    public void setStartDateFromEntity(LocalDate startDate) {
        if (startDate != null) {
            this.startDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE); // Format as ISO string (e.g., "2024-11-29")
        }
    }

    public void setEndDateFromEntity(LocalDate endDate) {
        if (endDate != null) {
            this.endDate = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE); // Format as ISO string
        }
    }

    public void setLastPaymentDateFromEntity(LocalDate lastPaymentDate) {
        if (lastPaymentDate != null) {
            this.lastPaymentDate = lastPaymentDate.format(DateTimeFormatter.ISO_LOCAL_DATE); // Format as ISO string
        }
    }

    // Utility method to convert String back to LocalDate (when setting from DTO to Entity)
    public LocalDate getStartDateAsEntity() {
        return this.startDate == null ? null : LocalDate.parse(this.startDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate getEndDateAsEntity() {
        return this.endDate == null ? null : LocalDate.parse(this.endDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate getLastPaymentDateAsEntity() {
        return this.lastPaymentDate == null ? null : LocalDate.parse(this.lastPaymentDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(Double startAmount) {
        this.startAmount = startAmount;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}

