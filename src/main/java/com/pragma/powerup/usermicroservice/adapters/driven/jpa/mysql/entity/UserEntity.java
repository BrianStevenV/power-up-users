package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private String dniNumber;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    @Schema(description = "Birthdate is optional -> The format is dd/MM/yyyy")
    @Nullable
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;
    private String password;
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_role")
    private RoleEntity idRole;

}
