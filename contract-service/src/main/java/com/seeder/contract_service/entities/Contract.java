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

}
