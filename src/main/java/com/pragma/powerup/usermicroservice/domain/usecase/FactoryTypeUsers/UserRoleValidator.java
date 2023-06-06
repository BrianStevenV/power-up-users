package com.pragma.powerup.usermicroservice.domain.usecase.FactoryTypeUsers;

import com.pragma.powerup.usermicroservice.domain.model.User;

public interface UserRoleValidator {
    void validate(User user);
}
