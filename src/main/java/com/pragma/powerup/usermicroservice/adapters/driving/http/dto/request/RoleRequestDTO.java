package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class RoleRequestDTO {
    private Long id;

    @JsonIgnore
    private String name;
    @JsonIgnore
    private String description;

    public RoleRequestDTO(Long id){
        this.id =id;
    }
}
