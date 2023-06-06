package com.pragma.powerup.usermicroservice.UserTest;


import com.pragma.powerup.usermicroservice.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.AgeNotValidException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UnauthorizedAccessException;
import com.pragma.powerup.usermicroservice.domain.model.ConstantsUseCase;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest//decimos que es clase de pruebas
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
        user.setBirthdate(LocalDate.of(1990, 1, 1));

        // Mocking
        when(authenticationUserInfoServicePort.getIdentifierUserFromToken()).thenReturn(verifyingRole);

        // Act
        userUseCase.saveUser(user);

        // Assert
        verify(authenticationUserInfoServicePort, times(1)).getIdentifierUserFromToken();
        verify(userPersistencePort, times(1)).saveUser(user);
    }

    @Test
    void saveUser_WithValidRoleAndInvalidAge_ExceptionThrown() {
        // Arrange
        String verifyingRole = ConstantsUseCase.PROVIDER_ROLE;
        User user = new User();
        user.setBirthdate(LocalDate.of(2005, 1, 1));

        // Mocking
        when(authenticationUserInfoServicePort.getIdentifierUserFromToken()).thenReturn(verifyingRole);

        // Act & Assert
        assertThrows(AgeNotValidException.class, () -> userUseCase.saveUser(user));

        // Verify
        verify(authenticationUserInfoServicePort, times(1)).getIdentifierUserFromToken();
        verify(userPersistencePort, never()).saveUser(user);
    }

    @Test
    void saveUser_UnauthorizedRole_ExceptionThrown() {
        // Arrange
        String verifyingRole = "OTHER_ROLE";
        User user = new User();
        user.setBirthdate(LocalDate.of(1990, 1, 1));

        // Mocking
        when(authenticationUserInfoServicePort.getIdentifierUserFromToken()).thenReturn(verifyingRole);

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () -> userUseCase.saveUser(user));

        // Verify
        verify(authenticationUserInfoServicePort, times(1)).getIdentifierUserFromToken();
        verify(userPersistencePort, never()).saveUser(user);
    }

    @Test
    void getUserByDni_ExistingDni_Success() {
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
    void getUserByDni_NonExistingDni_ReturnsNull() {
        // Arrange
        String dniNumber = "9876543210";
        when(userPersistencePort.getUserByDni(dniNumber)).thenReturn(null);

        // Act
        User result = userUseCase.getUserByDni(dniNumber);

        // Assert
        verify(userPersistencePort, times(1)).getUserByDni(dniNumber);
        Assertions.assertNull(result);
    }

}
