package com.pragma.powerup.usermicroservice.UserTest;

import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.MissingBirthdateValidationException;
import com.pragma.powerup.usermicroservice.domain.exceptions.AgeNotValidException;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;


@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest//decimos que es clase de pruebas
public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    private UserUseCase userUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userPersistencePort);
    }

    @Test
    public void saveUserWithValidData() {
        User user = new User(1L, "12345678A", "John", "Doe", "johndoe@mail.com", "123456789", LocalDate.of(2000, 1, 1), "password", new Role(2L, "Owner", "The owner of a restaurant"));

        userUseCase.saveUser(user);

        verify(userPersistencePort).saveUser(user);
    }

    @Test
    public void saveUserEmployeeWithNullBirthDate(){
        User user = new User(1L, "12345678A", "John", "Doe", "johndoe@mail.com", "123456789", null, "password", new Role(3L, "Owner", "The Employee of a restaurant"));

        userUseCase.saveUser(user);

        verify(userPersistencePort).saveUser(user);
    }


    @Test
    public void saveUserOwnerWithInvalidAgeException() {
        User user = new User(1L, "12345678A", "John", "Doe", "johndoe@mail.com", "123456789", LocalDate.of(2010, 1, 1), "password", new Role(2L, "Owner", "The owner of a restaurant"));

        assertThrows(AgeNotValidException.class, () -> userUseCase.saveUser(user));
    }

    @Test
    public void saveUserOwnerWithMissingBirthdateException() {
        User user = new User(1L, "12345678A", "John", "Doe", "johndoe@mail.com", "123456789", null, "password", new Role(2L, "Owner", "The owner of a restaurant"));

        assertThrows(MissingBirthdateValidationException.class, () -> userUseCase.saveUser(user));
    }

}
