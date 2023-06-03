package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserRequestDto {
    @Pattern(regexp = "^[0-9]+$")
    @NotBlank
    private String dniNumber;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    @NotBlank
    private String mail;
    @Pattern(regexp = "^[0-9+]{1,13}$")
    @NotBlank
    private String phone;
    @Schema(description = "Birthdate is optional -> The format is dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Nullable
    private LocalDate birthdate;
    @NotBlank
    private String password;
    private RoleRequestDTO idRole;
}
