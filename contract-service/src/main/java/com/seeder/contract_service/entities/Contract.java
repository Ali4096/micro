package com.seeder.contract_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "contract_status")
    @Enumerated(EnumType.STRING)
    private Status status;


    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "payment_frequency")
    private String paymentFrequency;

    @Column(name = "contract_amount")
    private Double contractAmount;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "outstanding_amount")
    private Double outstandingAmount;

    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate;

    @Column(name = "start_amount")
    private Double startAmount;

    private int fee;

    @Column(name = "user_id")
    private Long userId;

//    @Transient
    @Column(name = "cashkick_id")
    private Integer cashkickId;

    public enum Status {
        ACTIVE,
        INACTIVE,
        PENDING,
    }


    public Long getId() {
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

    public Integer getCashkickId() {
        return cashkickId;
    }


    public void setId(Long id) {
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

    public void setCashkickId(Integer cashkickId) {
        this.cashkickId = cashkickId;
    }
}
