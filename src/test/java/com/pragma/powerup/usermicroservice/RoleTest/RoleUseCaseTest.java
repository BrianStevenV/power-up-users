package com.pragma.powerup.usermicroservice.RoleTest;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.RoleUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class RoleUseCaseTest {
    @Mock
    private IRolePersistencePort rolePersistencePort;
    private RoleUseCase roleUseCase;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        roleUseCase = new RoleUseCase(rolePersistencePort);
    }


    @Test
    void getAllRoles_ReturnsListOfRoles() {
        // Arrange

        List<Role> expectedRoles = Arrays.asList(
                new Role(1L, "ADMIN_ROLE", "ADMIN_ROLE"),
                new Role(2L, "PROVIDER_ROLE", "PROVIDER_ROLE"),
                new Role(3L, "EMPLOYEE_ROLE", "EMPLOYEE_ROLE"),
                new Role(4L, "CLIENT_ROLE","CLIENT_ROLE")
        );
        when(rolePersistencePort.getAllRoles()).thenReturn(expectedRoles);

        // Act

        List<Role> result = roleUseCase.getAllRoles();

        // Assert

        verify(rolePersistencePort, times(1)).getAllRoles();
        Assertions.assertEquals(expectedRoles, result);
    }

    @Test
    void getAllRoles_ReturnsEmptyList() {
        // Arrange

        when(rolePersistencePort.getAllRoles()).thenReturn(Collections.emptyList());

        // Act

        List<Role> result = roleUseCase.getAllRoles();

        // Assert
        
        verify(rolePersistencePort, times(1)).getAllRoles();
        Assertions.assertTrue(result.isEmpty());
    }
}
