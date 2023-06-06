package com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers;

import com.pragma.powerup.usermicroservice.domain.model.ConstantsUseCase;
import com.pragma.powerup.usermicroservice.domain.model.User;

public class ClientRoleValidator implements UserRoleValidator{
    @Override
    public void validate(User user) {
        if(user.getIdRole().getId().equals(ConstantsUseCase.ID_CLIENT_REQUESTDTO));

    }
}
