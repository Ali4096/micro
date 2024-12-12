package com.seeder.user_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "user_name")
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    UserCredit userCredit;



}
