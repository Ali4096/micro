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


}