package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUserResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {
    private final IUserServicePort personServicePort;
    private final IUserRequestMapper personRequestMapper;
    private final IUserResponseMapper personResponseMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        personServicePort.saveUser(personRequestMapper.toUser(userRequestDto));
    }

    @Override
    public UserResponseDto getUserByDni(String dniNUmber) {
        return personResponseMapper.userToPersonResponse(personServicePort.getUserByDni(dniNUmber));
    }
}
