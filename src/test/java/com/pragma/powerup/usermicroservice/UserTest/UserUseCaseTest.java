package com.pragma.powerup.usermicroservice.UserTest;


import com.pragma.powerup.usermicroservice.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.AgeNotValidException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UnauthorizedAccessException;
import com.pragma.powerup.usermicroservice.domain.model.ConstantsUseCase;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers.AdministratorRoleValidator;
import com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers.ClientRoleValidator;
import com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers.EmployeeRoleValidator;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IAuthenticationUserInfoServicePort authenticationUserInfoServicePort;

    private UserUseCase userUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userPersistencePort, authenticationUserInfoServicePort);
    }

    @Test
    void saveUser_WithValidRoleAndAge_Success() {
        // Arrange
        String verifyingRole = ConstantsUseCase.ADMINISTRATOR_ROLE;
        User user = new User();
        Role role = new Role(3L, "PROVIDER_ROLE", "PROVIDER_ROLE");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setIdRole(role);

        // Mocking
        when(authenticationUserInfoServicePort.getIdentifierUserFromToken()).thenReturn(verifyingRole);

        // Act
        userUseCase.saveUser(user);

        // Assert
        verify(authenticationUserInfoServicePort, times(1)).getIdentifierUserFromToken();
        verify(userPersistencePort, times(1)).saveUser(user);
    }

    @Test
    void getUserByDniSuccess() {
        // Arrange
        String dniNumber = "1234567890";
        User expectedUser = new User();
        when(userPersistencePort.getUserByDni(dniNumber)).thenReturn(expectedUser);

        // Act
        User result = userUseCase.getUserByDni(dniNumber);

        // Assert
        verify(userPersistencePort, times(1)).getUserByDni(dniNumber);
        Assertions.assertEquals(expectedUser, result);
    }

    @Test
    void getUserByDniReturnsNull() {
        // Arrange
        String dniNumber = "9876543210";
        when(userPersistencePort.getUserByDni(dniNumber)).thenReturn(null);

        // Act
        User result = userUseCase.getUserByDni(dniNumber);

        // Assert
        verify(userPersistencePort, times(1)).getUserByDni(dniNumber);
        Assertions.assertNull(result);
    }

    @Test
    void saveUser_UnauthorizedAccessThrowsException() {
        // Arrange
        String verifyingRole = ConstantsUseCase.PROVIDER_ROLE;
        Role role = new Role(3L, "PROVIDER_ROLE", "PROVIDER_ROLE");
        User user = new User(5L,"5060","Test","Test","test@gmail.com","3192621109",LocalDate.of(1990, 1, 1),"string", role);

        // Act
        when(authenticationUserInfoServicePort.getIdentifierUserFromToken()).thenReturn(verifyingRole);

        // Assert
        Assertions.assertThrows(UnauthorizedAccessException.class, () -> {
            userUseCase.saveUser(user);
        });
    }

    @Test
    void saveUser_InvalidUserRoleThrowsException() {
        // Arrange
        String verifyingRole = ConstantsUseCase.ADMINISTRATOR_ROLE;
        User user = new User();
        user.setIdRole(new Role(5L, "INVALID_ROLE", "INVALID_ROLE"));

        // Act
        when(authenticationUserInfoServicePort.getIdentifierUserFromToken()).thenReturn(verifyingRole);

        // Assert
        Assertions.assertThrows(UnauthorizedAccessException.class, () -> {
            userUseCase.saveUser(user);
        });
    }

    @Test
    void saveUser_ValidationFailedThrowsException() {
        // Arrange
        String verifyingRole = ConstantsUseCase.ADMINISTRATOR_ROLE;
        Role role = new Role(3L, "PROVIDER_ROLE", "PROVIDER_ROLE");
        User user = new User(5L,"5060","Test","Test","test@gmail.com","3192621109",LocalDate.of(2020, 1, 1),"string", role);

        user.setBirthdate(LocalDate.of(2020, 1, 1)); // Invalid birthdate for provider role

        // Act
        when(authenticationUserInfoServicePort.getIdentifierUserFromToken()).thenReturn(verifyingRole);

        // Assert
        Assertions.assertThrows(AgeNotValidException.class, () -> {
            userUseCase.saveUser(user);
        });
    }

    // Cobertura de test a las clases que componen factoria sin ningun metodo.
    @Test
    void validateEmployeeRole_ValidRole_Success() {
        // Arrange
        EmployeeRoleValidator validator = new EmployeeRoleValidator();
        User user = new User();
        user.setIdRole(new Role(3L, "PROVIDER_ROLE", "PROVIDER_ROLE"));

        // Act
        Executable action = () -> validator.validate(user);

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void validateClientRole_ValidRole_Success() {
        // Arrange
        ClientRoleValidator validator = new ClientRoleValidator();
        User user = new User();
        user.setIdRole(new Role(1L, "CLIENT_ROLE", "CLIENT_ROLE"));

        // Act
        Executable action = () -> validator.validate(user);

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void validateClientRole_InvalidRole_Success() {
        // Arrange
        ClientRoleValidator validator = new ClientRoleValidator();
        User user = new User();
        user.setIdRole(new Role(5L, "INVALID_ROLE", "INVALID_ROLE"));

        // Act
        Executable action = () -> validator.validate(user);

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void validateAdministratorRole_EmptyMethod_NoExceptionThrown() {
        // Arrange
        AdministratorRoleValidator validator = new AdministratorRoleValidator();
        User user = new User();

        // Act
        Executable action = () -> validator.validate(user);

        // Assert
        assertDoesNotThrow(action);
    }

}
