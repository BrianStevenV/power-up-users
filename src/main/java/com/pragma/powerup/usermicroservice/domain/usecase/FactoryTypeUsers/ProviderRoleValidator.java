package com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.exceptions.AgeNotValidException;
import com.pragma.powerup.usermicroservice.domain.model.User;

import java.time.LocalDate;
import java.time.Period;

public class ProviderRoleValidator implements UserRoleValidator{
    @Override
    public void validate(User user) {
        if(user.getBirthdate() != null && !user.getBirthdate().equals("") && isAgeOwnerValidate(user.getBirthdate())){

        }   else{
            throw new AgeNotValidException();
        }
    }

    private boolean isAgeOwnerValidate(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        int age = Period.between(birthdate, today).getYears();
        return age > Constants.AGE_OF_OWNER;
    }

}
