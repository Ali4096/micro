package com.seeder.contract_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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


    @Transient
    private List<Long> cashkickIds;

    public enum Status {
        ACTIVE,
        INACTIVE,
        PENDING,
    }

}
