package com.seeder.user_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @JsonIgnore
    @Column(name = "user_name")
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    UserCredit userCredit;

}
