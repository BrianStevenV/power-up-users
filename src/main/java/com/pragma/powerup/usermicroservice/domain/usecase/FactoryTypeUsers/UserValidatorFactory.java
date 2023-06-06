package com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers;

import com.pragma.powerup.usermicroservice.domain.exceptions.UnauthorizedAccessException;
import com.pragma.powerup.usermicroservice.domain.model.ConstantsUseCase;

import java.util.HashMap;
import java.util.Map;

public class UserValidatorFactory {
    private Map<Long, UserRoleValidator> validatorMap;

    public UserValidatorFactory() {
        validatorMap = new HashMap<>();
        validatorMap.put(ConstantsUseCase.ID_ADMINISTRATOR_ROLE, new AdministratorRoleValidator());
        validatorMap.put(ConstantsUseCase.ID_PROVIDER_REQUESTDTO, new ProviderRoleValidator());
        validatorMap.put(ConstantsUseCase.ID_EMPLOYEE_REQUESTDTO, new EmployeeRoleValidator());
        validatorMap.put(ConstantsUseCase.ID_CLIENT_REQUESTDTO, new ClientRoleValidator());
    }

    public UserRoleValidator getValidator(Long roleId) {
        UserRoleValidator validator = validatorMap.get(roleId);
        if (validator == null) {
            throw new UnauthorizedAccessException();
        }
        return validator;
    }
}
