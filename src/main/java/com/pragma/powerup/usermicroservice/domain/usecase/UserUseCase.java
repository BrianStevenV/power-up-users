package com.pragma.powerup.usermicroservice.domain.usecase;


import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.AgeNotValidException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UnauthorizedAccessException;
import com.pragma.powerup.usermicroservice.domain.model.ConstantsUseCase;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers.UserRoleValidator;
import com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers.UserValidatorFactory;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    private final IAuthenticationUserInfoServicePort authenticationUserInfoServicePort;

    public UserUseCase(IUserPersistencePort personPersistencePort, IAuthenticationUserInfoServicePort authenticationUserInfoServicePort) {
        this.userPersistencePort = personPersistencePort;
        this.authenticationUserInfoServicePort = authenticationUserInfoServicePort;
    }
//    @Override
//    public void saveUser(User user) {
//        String verifyingRole = authenticationUserInfoServicePort.getIdentifierUserFromToken();
//        if (isUserAuthorized(verifyingRole, user)) {
//            if (ConstantsUseCase.PROVIDER_ROLE.equals(verifyingRole)) {
//                validateAgeForRole(verifyingRole, user);
//            }
//            userPersistencePort.saveUser(user);
//        } else {
//            throw new UnauthorizedAccessException();
//        }
//    }

//    @Override
//    public void saveUser(User user) {
//        String verifyingRole = authenticationUserInfoServicePort.getIdentifierUserFromToken();
//        if(ConstantsUseCase.ADMINISTRATOR_ROLE.equals(verifyingRole)){
//            userPersistencePort.saveUser(user);
//        } else if (user.getIdRole().getId().equals(ConstantsUseCase.ID_PROVIDER_REQUESTDTO) && ConstantsUseCase.ADMINISTRATOR_ROLE.equals(verifyingRole)) {
//           if(user.getBirthdate() != null && !user.getBirthdate().equals("") && !isAgeOwnerValidate(user.getBirthdate())){
//               userPersistencePort.saveUser(user);
//           }
//            throw new AgeNotValidException();
//        } else if (user.getIdRole().getId().equals(ConstantsUseCase.ID_EMPLOYEE_REQUESTDTO) && ConstantsUseCase.PROVIDER_ROLE.equals(verifyingRole)) {
//            userPersistencePort.saveUser(user);
//        } else if (user.getIdRole().getId().equals(ConstantsUseCase.ID_CLIENT_REQUESTDTO)) {
//            userPersistencePort.saveUser(user);
//        }   else{
//            throw new UnauthorizedAccessException();
//        }
//    }

//    @Override
//    public void saveUser(User user) {
//        String verifyingRole = authenticationUserInfoServicePort.getIdentifierUserFromToken();
//        Long userRoleId = user.getIdRole().getId();
//
//        UserValidatorFactory validatorFactory = new UserValidatorFactory();
//        UserRoleValidator validator = validatorFactory.getValidator(userRoleId);
//
//        if (userRoleId.equals(ConstantsUseCase.ID_PROVIDER_REQUESTDTO) && !ConstantsUseCase.ADMINISTRATOR_ROLE.equals(verifyingRole)) {
//            throw new UnauthorizedAccessException();
//        } else if (userRoleId.equals(ConstantsUseCase.ID_EMPLOYEE_REQUESTDTO) && !ConstantsUseCase.PROVIDER_ROLE.equals(verifyingRole)) {
//            throw new UnauthorizedAccessException();
//        }
//
//        validator.validate(user);
//        userPersistencePort.saveUser(user);
//    }

    @Override
    public void saveUser(User user) {
        String verifyingRole = authenticationUserInfoServicePort.getIdentifierUserFromToken();
        Long userRoleId = user.getIdRole().getId();

        UserValidatorFactory validatorFactory = new UserValidatorFactory();
        UserRoleValidator validator = validatorFactory.getValidator(userRoleId);

        if (!authorizationCreateUser(verifyingRole, userRoleId)) {
            throw new UnauthorizedAccessException();
        }

        validator.validate(user);
        userPersistencePort.saveUser(user);
    }

    private boolean authorizationCreateUser(String verifyingRole, Long userRoleId) {
        if (userRoleId.equals(ConstantsUseCase.ID_PROVIDER_REQUESTDTO)) {
            return ConstantsUseCase.ADMINISTRATOR_ROLE.equals(verifyingRole);
        } else if (userRoleId.equals(ConstantsUseCase.ID_EMPLOYEE_REQUESTDTO)) {
            return ConstantsUseCase.PROVIDER_ROLE.equals(verifyingRole);
        } else if (ConstantsUseCase.ID_CLIENT_REQUESTDTO.equals(userRoleId)) {
            return true;
        } else {
            throw new UnauthorizedAccessException();
        }

    }



    //TODO: pasar a constantes

    @Override
    public User getUserByDni(String dniNumber) {
        return userPersistencePort.getUserByDni(dniNumber);
    }


    private boolean isAgeOwnerValidate(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        int age = Period.between(birthdate, today).getYears();
        return age > Constants.AGE_OF_OWNER;
    }

}
