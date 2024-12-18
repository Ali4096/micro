package com.seeder.cashkick_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "cashkicks")
public class Cashkick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cashkick_name")
    private String cashkickName;

    @Column(name = "cashkick_status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "total_received")
    private Double totalReceived;

    @Column(name = "term_length")
    private Integer termLength;

    private Double avgFee;

    @Column(name = "user_id")
    private Long userId;

    @Transient
    private List<Long> contractIds;


    public enum Status {
        PENDING,
        APPROVED
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCashkickName() {
        return cashkickName;
    }

    public void setCashkickName(String cashkickName) {
        this.cashkickName = cashkickName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Double getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(Double totalReceived) {
        this.totalReceived = totalReceived;
    }

    public Integer getTermLength() {
        return termLength;
    }

    public void setTermLength(Integer termLength) {
        this.termLength = termLength;
    }

    public Double getAvgFee() {
        return avgFee;
    }

    public void setAvgFee(Double avgFee) {
        this.avgFee = avgFee;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getContractIds() {
        return contractIds;
    }

    public void setContractIds(List<Long> contractIds) {
        this.contractIds = contractIds;
    }
}