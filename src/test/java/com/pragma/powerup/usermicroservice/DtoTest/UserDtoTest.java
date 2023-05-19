package com.pragma.powerup.usermicroservice.DtoTest;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RoleRequestDTO;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserRequestDtoValid() {
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO(1L, "admin","role admin");

        UserRequestDto userRequestDto = new UserRequestDto("12345678","John","Doe","johndoe@example.com","+1234567890",LocalDate.of(2000, 1, 1),"password",roleRequestDTO);


        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testUserRequestDtoInvalidBlank() {
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO(1L, "admin","role admin");

        UserRequestDto userRequestDto = new UserRequestDto("12345678","Juan","Doe","","",LocalDate.of(2000, 1, 1),"password",roleRequestDTO);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        assertEquals(3, violations.size());
    }

    @Test
    public void testUserRequestDtoInvalidFormat() {
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO(1L, "admin","role admin");

        UserRequestDto userRequestDto = new UserRequestDto("12345A","John", "Doe", "invalid-email-format", "invalid-phone-format", LocalDate.of(2000, 1, 1),"password",roleRequestDTO);


        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        assertEquals(3, violations.size());
    }
}
