package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.MissingBirthdateValidationException;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.AgeNotValidException;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort personPersistencePort) {
        this.userPersistencePort = personPersistencePort;
    }

    @Override
    public void saveUser(User user) {
        if (user.getIdRole().getId() == Constants.PROVIDER_ROLE_ID) {
            if (user.getBirthdate() != null && !user.getBirthdate().equals("")) {
                if(isAgeOwnerValidate(user.getBirthdate())){
                    userPersistencePort.saveUser(user);
                }   else{
                    throw new AgeNotValidException("The Owner must be 18 years older or more.");
                }
            } else {
                throw new MissingBirthdateValidationException("Birthdate is required and the format is: yyyy-MM-dd.");
            }
        } else {
            userPersistencePort.saveUser(user);
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
