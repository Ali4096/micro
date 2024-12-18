package com.seeder.user_service.dtos;

import com.seeder.user_service.entities.UserCredit;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class UserDTO {

    @NotNull(message = "ID cannot be null")
    private Integer id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    // We don't validate password in the DTO since it's sensitive data
//    private String password;

    private UserCredit userCredit;  // Nested DTO

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserCredit getUserCredit() {
        return userCredit;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserCredit(UserCredit userCredit) {
        this.userCredit = userCredit;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userCredit=" + userCredit +
                '}';
    }
}

