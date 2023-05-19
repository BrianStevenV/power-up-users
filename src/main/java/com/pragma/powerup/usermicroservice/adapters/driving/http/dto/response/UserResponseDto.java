package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserResponseDto {
    private String dniNumber;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private LocalDate birthdate;
    private String password;
    private RoleResponseDto idRole;
}
