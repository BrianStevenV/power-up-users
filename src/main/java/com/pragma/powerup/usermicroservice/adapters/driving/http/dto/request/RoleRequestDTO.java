package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleRequestDTO {
    private Long id;
    private String name;
    private String description;

    public RoleRequestDTO(Long id){
        this.id =id;
    }
}
