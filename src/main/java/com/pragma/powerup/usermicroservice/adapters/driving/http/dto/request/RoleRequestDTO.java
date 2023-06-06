package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoleRequestDTO {
    private Long id;
    private String name;
    private String description;

    public RoleRequestDTO(Long id){
        this.id =id;
    }
}
