package com.seeder.cashkick_service.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    public Long getId() {
        return id;
    }

    public UserDTO(Long id) {
        this.id = id;
    }
}

