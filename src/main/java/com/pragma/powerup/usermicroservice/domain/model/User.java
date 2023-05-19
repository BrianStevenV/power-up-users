package com.pragma.powerup.usermicroservice.domain.model;

import java.time.LocalDate;


public class User {
    private Long id;
    private String dniNumber;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private LocalDate birthdate;
    private String password;
    private Role idRole;

    public User(Long id, String dniNumber, String name, String surname, String mail, String phone, LocalDate birthdate, String password, Role idRole) {
        this.id = id;
        this.dniNumber = dniNumber;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.phone = phone;
        this.birthdate = birthdate;
        this.password = password;
        this.idRole = idRole;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber(String dniNumber) {
        this.dniNumber = dniNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getIdRole() {
        return idRole;
    }

    public void setIdRole(Role idRole) {
        this.idRole = idRole;
    }
}
